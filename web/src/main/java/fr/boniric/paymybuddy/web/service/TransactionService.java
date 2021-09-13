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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class TransactionService {
    @Autowired
    UserService userService;

    @Autowired
    TransactionProxy transactionProxy;

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private ContactService contactService;

    public Transaction transactionServicePost(Transaction transaction, Model model) {

        double commission = 0.005; // 0.5 %
        double amountTransaction = transaction.getTransactionAmount();
        double amountTransactionFinal = Math.round(amountTransaction * 100.0) / 100.0;
        double amountCommission = amountTransactionFinal * commission;
        double amountCommissionFinal = Math.round(amountCommission*100.0)/100.0;
        double amountTotalCommission = Math.round((amountTransactionFinal + amountCommissionFinal)*100.0)/100.0;

        User userIdPayer = userService.getUserByEmail(EMAILUSER_AUTHENTICATE);
        transaction.setTransactionCommissionAmount(amountCommissionFinal);
        transaction.setUserId(userIdPayer.getId()); // return user id
        String contactSelected = transaction.getListEmail(); // contact sélectionned

        if (contactSelected != null) {
            //extract email
            String[] words = contactSelected.split(": ");
            for (String word : words) {
                if (word.contains("@")) {

                    // Récupère l'adresse Email, recherche l'id User (Contact Id)
                    User userIdContact = userService.getUserByEmail(word);
                    double balanceUser = userIdPayer.getBalance();

                    // si le type de payment est compte (1 = compte, 2 = rib)
                    int typePayment = transaction.getPaymentTypeId();
                    if (typePayment==1){

                    // vérifie le montant du compte
                    if (balanceUser < amountTransactionFinal){

                        model.addAttribute("status",
                                "Your balance is: "+balanceUser+" €");

                        model.addAttribute("firstname", userIdContact.getFirstname());
                        model.addAttribute("lastname", userIdContact.getLastname());
                        model.addAttribute("email", userIdContact.getEmail());
                        model.addAttribute("displayBtnPay",false);

                    } else {

                        model.addAttribute("status",
                                "Please confirm your payment");
                        // envoi au formulaire
                        model.addAttribute("firstname", userIdContact.getFirstname());
                        model.addAttribute("lastname", userIdContact.getLastname());
                        model.addAttribute("email", userIdContact.getEmail());
                        model.addAttribute("displayBtnPay",true);
                        transaction.setTransactionTotalAmount(amountTotalCommission);
                        transaction.setContactId(userIdContact.getId());

                        //Save Transaction
                        transactionProxy.saveTransaction(transaction);
                    }

                    } else {
                        model.addAttribute("status",
                                "Please confirm your payment with Rib");
                        // envoi au formulaire
                        model.addAttribute("firstname", userIdContact.getFirstname());
                        model.addAttribute("lastname", userIdContact.getLastname());
                        model.addAttribute("email", userIdContact.getEmail());
                        transaction.setTransactionTotalAmount(amountTotalCommission);
                        transaction.setContactId(userIdContact.getId());

                        //Save Transaction
                        transactionProxy.saveTransaction(transaction);

                    }


                    // mise a jour table user

                    // le payeur doit payer amount totol commission en moins sur la  balance
                    // le bénéficiaire crédit en plus
                    // crédit si il se recharge aussi



                }
            }
        }
        return transaction;
    }

    public void transactionServiceGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // get email user authenticate
        EMAILUSER_AUTHENTICATE = auth.getName();
        User user = userService.getUserByEmail(EMAILUSER_AUTHENTICATE);
        String strList = contactService.listOfContact(user.getId());

        double balanceUser = user.getBalance();
        List<String> listContact = new ArrayList<>();
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
    }



    public void saveTransaction(Transaction transaction) { transactionProxy.saveTransaction(transaction); }
}
