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

    String EMAILUSER_AUTHENTICATE;

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transfer")
    public String loginToTransfer(Model model) {
        transactionService.pushNewLoginToTransfer(model);
        System.out.println("pushNewLoginToTransfer Web Call");
        model.addAttribute("rows", transactionService.LIST_TRANSACTIONDTO);
        return "/transfer";
    }

    @PostMapping(value = "/transfer")
    public String transferToRecapTransaction(@ModelAttribute("transaction") Transaction transaction, Model model) {
        System.out.println("pushNewsTransferToRecapTransaction Web Call ");
        return transactionService.pushNewsTransferToRecapTransaction(transaction, model); // if control ok return recapTransaction else return transfer
    }

    @PostMapping(value = "/transaction", params = "action=Pay") // TODO transaction
    public String recapTransactionPay(Model model) {
        //Click Pay
        Transaction transaction = transactionService.RESULT_TRANSACTION; // get Transaction

        // Selector Operation Payment
        if (transactionService.TYPEPAYMENT_SELECTOR == 1) {
            transactionService.creditUserBeneficiary(transaction); // Credit User
            transactionService.debitPayer(transaction); // Debit User Payer

        } else if (transactionService.TYPEPAYMENT_SELECTOR == 2) {
            transactionService.creditUserBeneficiary(transaction); // Credit User without débit
            transactionService.saveTransaction(transaction); // Save transaction

        } else if (transactionService.TYPEPAYMENT_SELECTOR == 3) {
            transactionService.debitPayer(transaction); // Debit User Payer
        }

        transactionService.pushNewLoginToTransfer(model);
        model.addAttribute("rows", transactionService.LIST_TRANSACTIONDTO);
        return "/transfer";
    }

    @PostMapping(value = "/transaction", params = "action=Cancel") // Todo transaction
    public String recapTransactionCancel(Model model) {
        // click Cancel User
        transactionService.pushNewLoginToTransfer(model);
        model.addAttribute("rows", transactionService.LIST_TRANSACTIONDTO);
        return "/transfer";
    }

    @GetMapping("/transaction/{id}")
    public String getListTransaction(@PathVariable("id") int userId) {
        return transactionService.getListTransaction(userId);
    }
}
