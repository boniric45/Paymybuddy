package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.web.model.TransactionDto;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.TransactionProxy;
import lombok.Data;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class TransactionService {
    public Transaction RESULT_TRANSACTION;
    public List<String> LIST_TRANSACTION = new ArrayList<>();
    public List<TransactionDto> LIST_TRANSACTIONDTO = new ArrayList<>();
    public Boolean RELOADING = false;
    @Autowired
    UserService userService;
    @Autowired
    TransactionProxy transactionProxy;
    private String EMAILUSER_AUTHENTICATE;
    @Autowired
    private ContactService contactService;

    public void pushNewLoginToTransfer(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // get email user authenticate
        EMAILUSER_AUTHENTICATE = auth.getName(); // get email authenticate
        User user = userService.getUserByEmail(EMAILUSER_AUTHENTICATE); // get user by email
        Transaction transaction = new Transaction();

        int userAuthId = user.getId(); // get userId
        String strList = contactService.listOfContact(userAuthId); // get Listcontact
        String strListTransaction = getListTransaction(userAuthId); // get ListTransaction

        double balanceUser = user.getBalance(); // get balance user authenticate

        // Object result list of contact by user authenticate
        List<String> listContact = new ArrayList<>();
        JSONArray ja = new JSONArray(strList);
        for (Object o : ja) {
            CDL.rowToString(ja);
            listContact.add((String) o);
            model.addAttribute("list", listContact); // push list contact
        }


        // Object result list transaction by user authenticate
        List<String> listTransaction = new ArrayList<>();
        List<TransactionDto> transactionDtoList = LIST_TRANSACTIONDTO;
          String[] rows;
        JSONArray jaTransaction = new JSONArray(strListTransaction);
        JSONObject jsonObject = new JSONObject();

        for (Object obj : jaTransaction) {
            CDL.rowToString(jaTransaction);
            String row = obj.toString();
            rows = row.split("-");
            listTransaction.addAll(Arrays.asList(rows));
            TransactionDto transactionDto = new TransactionDto(rows[0],rows[1],rows[2]);
            transactionDtoList.addAll(Collections.singleton(transactionDto));
        }


        // push news in forms transfer
        model.addAttribute("transaction", transaction);
        model.addAttribute("userEmail", EMAILUSER_AUTHENTICATE); // push name authenticate
        model.addAttribute("amount", "0€"); // push value
        model.addAttribute("description", "Description"); // push description
        model.addAttribute("balance", balanceUser + " €"); // push balance user

        LIST_TRANSACTION = listTransaction;
//        model.addAttribute("rows", listTransaction); // push list transaction


    }


    public String pushNewsTransferToRecapTransaction(Transaction transaction, Model model) {

        User userSelected = searchContact(transaction.getListEmail()); // get Object User Selected
        int typePayment = transaction.getPaymentTypeId(); // type de payment
        double amountTransaction = transaction.getTransactionAmount(); // montant
        String description = transaction.getDescription(); // description

        // control
        return controlTypePayment(typePayment, transaction, model);
    }

    public String controlTypePayment(int typePayment, Transaction transaction, Model model) {

        User userPayer = userService.getUserByEmail(EMAILUSER_AUTHENTICATE); // get User Payer with email authenticate
        User userSelected = searchContact(transaction.getListEmail()); // get Object User Selected
        double amountTransaction = transaction.getTransactionAmount();
        String selector = "";

        // Payment Type Account
        if (typePayment == 1) {

            //if the pay is identical to the selected user
            if (Objects.equals(userPayer.getId(), userSelected.getId())) {
                selector = "/transfer";
                model.addAttribute("statut", "Please use rib payment");
            }
            // if amount is <= 0
            else if (amountTransaction <= 0) {
                selector = "/transfer";
                model.addAttribute("statut", "Amount unauthorized");
            }
            // insufficient supply
            else if (amountTransaction > userPayer.getBalance()) {
                selector = "/transfer";
                model.addAttribute("statut", "insufficient supply, Please use rib payment");
            }
            // payment
            else {
                selector = "/recapTransaction";
                calculTransactionPushRecapTransaction(model, transaction);

            }
        }

        // Payment Type Rib
        if (typePayment == 2) {

            // if amount is <= 0
            if (amountTransaction <= 0) {
                selector = "/transfer";
                model.addAttribute("statut", "Amount unauthorized");
            } else if (Objects.equals(userPayer.getId(), userSelected.getId())) {
                selector = "/recapTransaction";
                calculTransactionPushRecapTransaction(model, transaction);
                RELOADING = true;
            }

            // payment
            else {
                selector = "/recapTransaction";
                calculTransactionPushRecapTransaction(model, transaction);
            }
        }

        return selector;
    }

    public void calculTransactionPushRecapTransaction(Model model, Transaction transaction) {

        double commission = 0.005; // 0.5 %
        double amountTransaction = transaction.getTransactionAmount();
        double amountTransactionFinal = Math.round(amountTransaction * 100.0) / 100.0;

        double amountCommission = amountTransactionFinal * commission;
        double amountCommissionFinal = Math.round(amountCommission * 100.0) / 100.0;

        double amountTotalWithCommission = amountTransactionFinal + amountCommissionFinal;

        User userPayer = userService.getUserByEmail(EMAILUSER_AUTHENTICATE); // get User Payer with email authenticate
        User userSelected = searchContact(transaction.getListEmail()); // get Object User Selected

        double balanceUserPayer = userPayer.getBalance(); // get balance user authenticate
        double balanceUserSelected = userSelected.getBalance(); // get balance user selected

        transaction.setTransactionAmount(amountTransactionFinal);
        transaction.setTransactionCommissionAmount(amountCommissionFinal);// Push amount commission in Object Transaction
        transaction.setTransactionTotalAmount(amountTotalWithCommission);
        transaction.setUserId(userPayer.getId());
        transaction.setContactId(userSelected.getId());

        // Push Forms recapTransaction
        model.addAttribute("status", "Please confirm your payment");
        model.addAttribute("firstname", userSelected.getFirstname());
        model.addAttribute("lastname", userSelected.getLastname());
        model.addAttribute("email", userSelected.getEmail());
        model.addAttribute("description", transaction.getDescription());
        model.addAttribute("transactionAmount", transaction.getTransactionAmount());
        model.addAttribute("transactionCommissionAmount", transaction.getTransactionCommissionAmount());
        model.addAttribute("transactionTotalAmount", transaction.getTransactionTotalAmount());
        RESULT_TRANSACTION = transaction;
    }

    public void creditUserBeneficiary(Transaction transaction) {
        double amountTransactionFinal = transaction.getTransactionAmount(); // get amount credit
        User userSelected = searchContact(transaction.getListEmail()); // get Object User Selected
        double balanceUserSelected = userSelected.getBalance(); // get balance user selected

        // Update Beneficiary
        double amountBalanceUserSelected = balanceUserSelected + amountTransactionFinal;
        userService.updateUser(userSelected.getId(), amountBalanceUserSelected);

    }

    public void debitPayer(Transaction transaction) {

        User userPayer = userService.getUserByEmail(EMAILUSER_AUTHENTICATE); // get User Payer with email authenticate
        double balancePayer = userPayer.getBalance();
        double amountTotalWithCommission = transaction.getTransactionTotalAmount();

        // Update Payer
        double balancePayerResult = balancePayer - amountTotalWithCommission;
        userService.updateUser(userPayer.getId(), Math.round(balancePayerResult * 100.0) / 100.0);
        saveTransaction(transaction);

    }

    public User searchContact(String contactSelected) {
        User userSelected = new User();
        if (contactSelected != null) {
            //extract email
            String[] words = contactSelected.split(": ");
            for (String word : words) {
                if (word.contains("@")) {

                    // Récupère l'adresse Email, recherche l' User (Contact Id)
                    userSelected = userService.getUserByEmail(word);
                }
            }
        }
        return userSelected;
    }

    public void saveTransaction(Transaction transaction) {
        transactionProxy.saveTransaction(transaction);
    }

    public String getListTransaction(Integer userId) {
        return transactionProxy.getTransaction(userId);
    }

}
