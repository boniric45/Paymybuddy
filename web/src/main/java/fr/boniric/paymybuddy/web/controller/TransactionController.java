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
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileNotFoundException;

@Controller
public class TransactionController {

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public String transactionform(@ModelAttribute("transaction") Transaction transaction, Model model) throws FileNotFoundException {
        transactionService.transactionServicePost(transaction, model);
        return "/recapTransaction";
    }

    @PostMapping("/recapTransaction")
    public String recapTransaction(Model model) {
        transactionService.transactionServiceGet(model);

        return "/transfer";
    }


    @GetMapping("/transfer")
    public String success(Model model) {
        transactionService.transactionServiceGet(model);
        return "transfer";
    }

}
