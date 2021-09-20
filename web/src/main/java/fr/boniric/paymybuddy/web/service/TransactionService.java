package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.TransactionProxy;
import lombok.Data;
import org.json.CDL;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Service
public class TransactionService {
    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    UserService userService;

    @Autowired
    TransactionProxy transactionProxy;

    @Autowired
    private ContactService contactService;

    public void transactionServicePost(Transaction transaction, Model model, Boolean PAYED) {

        double commission = 0.005; // 0.5 %
        double amountTransaction = transaction.getTransactionAmount();
        double amountTransactionFinal = Math.round(amountTransaction * 100.0) / 100.0;

        double amountCommission = amountTransactionFinal * commission;
        double amountCommissionFinal = Math.round(amountCommission * 100.0) / 100.0;

        double amountTotalCommission = amountTransactionFinal + amountCommissionFinal;

        User userPayer = userService.getUserByEmail(EMAILUSER_AUTHENTICATE); // get User Payer with email authenticate
        User userSelected = searchContact(transaction.getListEmail()); // get Object User Selected

        double balanceUserPayer = userPayer.getBalance(); // get balance user authenticate
        double balanceUserSelected = userSelected.getBalance(); // get balance user selected

        transaction.setTransactionCommissionAmount(amountCommissionFinal); // Push amount commission in forms recapTransaction
        transaction.setTransactionTotalAmount(amountTotalCommission); // Push amount in forms recapTransaction
        transaction.setUserId(userPayer.getId());

        // load Transaction and push list in forms
        List<Transaction> listTransaction = new ArrayList<>();
        model.addAttribute("listTransaction", listTransaction);

        int typePayment = transaction.getPaymentTypeId();

        // Payment Type Account
        if (typePayment == 1) {
            // vérify if amount is > amount transaction
            if (balanceUserPayer < amountTransactionFinal) {
                PAYED = false;
                // Push Forms recapTransaction
                model.addAttribute("status", "Your balance is: " + balanceUserPayer + " €");
                model.addAttribute("firstname", userSelected.getFirstname());
                model.addAttribute("lastname", userSelected.getLastname());
                model.addAttribute("email", userSelected.getEmail());
                model.addAttribute("displayBtnPay", false);

                // verify if payer is user selected
            } else if (Objects.equals(userPayer.getId(), userSelected.getId())) {
                PAYED = false;
                // Payment Balance //
                model.addAttribute("status", "Please use rib payment");
                model.addAttribute("firstname", userSelected.getFirstname());
                model.addAttribute("lastname", userSelected.getLastname());
                model.addAttribute("email", userSelected.getEmail());
                model.addAttribute("displayBtnPay", false);

            } else {
                // Push Forms recapTransaction
                model.addAttribute("status", "Please confirm your payment");
                model.addAttribute("firstname", userSelected.getFirstname());
                model.addAttribute("lastname", userSelected.getLastname());
                model.addAttribute("email", userSelected.getEmail());
                model.addAttribute("displayBtnPay", true);
                transaction.setContactId(userSelected.getId());
            }

            if (PAYED) {

                // Push Forms recapTransaction
                model.addAttribute("status", "Please confirm your payment");
                model.addAttribute("firstname", userSelected.getFirstname());
                model.addAttribute("lastname", userSelected.getLastname());
                model.addAttribute("email", userSelected.getEmail());
                model.addAttribute("displayBtnPay", true);
                transaction.setContactId(userSelected.getId());

                // credit beneficiary
                creditBeneficiary(balanceUserSelected, amountTransactionFinal, userSelected, transaction);

                // Debit Payer
                debitPayer(balanceUserPayer, amountTotalCommission, userPayer, transaction);

                //Save Transaction
                transactionProxy.saveTransaction(transaction);

            }
        }

        if (typePayment == 2 && PAYED) {
            // Payment Type Rib
            transaction.setTransactionCommissionAmount(amountCommissionFinal); // Push amount commission in forms recapTransaction
            transaction.setTransactionTotalAmount(amountTotalCommission); // Push amount in forms recapTransaction
            model.addAttribute("status", "Please confirm your payment with Rib");

            // Push Forms
            model.addAttribute("firstname", userSelected.getFirstname());
            model.addAttribute("lastname", userSelected.getLastname());
            model.addAttribute("email", userSelected.getEmail());
            transaction.setTransactionTotalAmount(amountTotalCommission);
            model.addAttribute("displayBtnPay", true);

            // credit beneficiary
            creditBeneficiary(balanceUserSelected, amountTransactionFinal, userSelected, transaction);

            // load Transaction and push list in forms
            model.addAttribute("listTransaction", listTransaction);

            //Save Transaction
              transactionProxy.saveTransaction(transaction);

        }
    }

    public void recupInfo(){}

    public void payment(){}

    public void creditBeneficiary(double balanceUserSelected, double amountTransactionFinal, User userSelected, Transaction transaction) {

        // Update Beneficiary
        double amountBalanceUserSelected = balanceUserSelected + amountTransactionFinal;
        userService.updateUser(userSelected.getId(), amountBalanceUserSelected);

    }

    public void debitPayer(double balancePayer, double amountTotalCommission, User userPayer, Transaction transaction) {

        // Update Payer
        double balancePayerResult = balancePayer - amountTotalCommission;
        userService.updateUser(userPayer.getId(), Math.round(balancePayerResult * 100.0) / 100.0);

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

    public void transactionServiceGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // get email user authenticate
        EMAILUSER_AUTHENTICATE = auth.getName();
        User user = userService.getUserByEmail(EMAILUSER_AUTHENTICATE);
        int userAuthId = user.getId();
        String strList = contactService.listOfContact(userAuthId);
        String strListTransaction = getListTransaction(userAuthId);

        double balanceUser = user.getBalance();
        List<String> listContact = new ArrayList<>();
        List<String> listTransaction = new ArrayList<>();
        model.addAttribute("userEmail", EMAILUSER_AUTHENTICATE); // push name authenticate
        model.addAttribute("balance", balanceUser + " €"); // push balance user

        // Object result list of contact
        JSONArray ja = new JSONArray(strList);
        for (Object o : ja) {
            CDL.rowToString(ja);
            listContact.add((String) o);
            model.addAttribute("list", listContact); // push list contact
        }

        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);

        JSONArray jaTransaction = new JSONArray(strListTransaction);
        for (Object obj : jaTransaction) {
            CDL.rowToString(jaTransaction);
            listTransaction.add((String) obj);
            model.addAttribute("transactionList", listTransaction); // push list transaction
        }

    }

    public void saveTransaction(Transaction transaction) {
        transactionProxy.saveTransaction(transaction);
    }

    public String getListTransaction(Integer userId) {
        return transactionProxy.getTransaction(userId);
    }
}
