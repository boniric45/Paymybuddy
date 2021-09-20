package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.web.service.ContactService;
import fr.boniric.paymybuddy.web.service.TransactionService;
import fr.boniric.paymybuddy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer") // Todo 1
    public String success(Model model) {
        transactionService.transactionServiceGet(model);
        System.out.println("Transfer Get > 1");
        return "/transfer";
    }

    @PostMapping(value = "/transfer")
    public String transactionform(@ModelAttribute("transaction") Transaction transaction, Model model) {
        transactionService.transactionServicePost(transaction, model,false);
        // TODO ok transaction alimenté
        System.out.println("Transfer Post > 2 étape > "+transaction.getTransactionTotalAmount()+" user > "+transaction.getUserId()+" contact > "+transaction.getContactId());
        return "/recapTransaction";
    }

    // Todo troisième après valid
    @PostMapping(value = "/recapTransaction", params = "action=Pay")
    public String recapTransaction(@ModelAttribute("transaction") Transaction transaction,Model model) {

        //Click Pay
        transactionService.transactionServicePost(transaction, model,true);
        System.out.println("recapTransaction > 3 étape > Click Pay : "+transaction.getTransactionTotalAmount());
        transactionService.transactionServiceGet(model);    // affiche les infos de transfer
        return "/transfer";
    }


// Todo Cancel
    @PostMapping(value = "/recapTransaction", params = "action=Cancel")
    public String recapTransactionCancel(Model model) {
        // click Cancel
        System.out.println("recapTransaction > 3 étape > Click Cancel");
        transactionService.transactionServiceGet(model);
        return "/transfer";
    }


    @GetMapping("/transaction/{id}")
    public String getListTransaction(@PathVariable("id") int userId) {
        return transactionService.getListTransaction(userId);
    }

}
