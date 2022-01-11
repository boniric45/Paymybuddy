package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.web.model.TransactionDto;
import fr.boniric.paymybuddy.web.service.ContactService;
import fr.boniric.paymybuddy.web.service.TransactionService;
import fr.boniric.paymybuddy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String loginToTransfer(HttpServletRequest request, Model model) {
        transactionService.pushNewLoginToTransfer(model);
        System.out.println("pushNewLoginToTransfer Web Call");
        model.addAttribute("rows", transactionService.LIST_TRANSACTIONDTO);
        return "/transfer";
    }


    @PostMapping(value = "/transfer")
    public String transferToRecapTransaction(@ModelAttribute("transaction") Transaction transaction, Model model) {
        transactionService.pushNewLoginToTransfer(model);
        System.out.println("pushNewsTransferToRecapTransaction Web Call > ");
        return transactionService.pushNewsTransferToRecapTransaction(transaction, model); // if control ok return recapTransaction else return transfer
    }

    @PostMapping(value = "/recapTransaction", params = "action=Pay")
    public String recapTransactionPay(Model model) {
        //Click Pay
        Transaction transaction = transactionService.RESULT_TRANSACTION; // get Transaction

        // Reloading User
        if (transactionService.RELOADING) {
            transactionService.creditUserBeneficiary(transaction); // Credit User without débit
            transactionService.saveTransaction(transaction); // Save transaction
        } else {
            transactionService.creditUserBeneficiary(transaction); // Credit User
            transactionService.debitPayer(transaction); // Debit User Payer
        }
        transactionService.pushNewLoginToTransfer(model);
        model.addAttribute("rows", transactionService.LIST_TRANSACTION);
        return "/transfer";
    }

    @PostMapping(value = "/recapTransaction", params = "action=Cancel")
    public String recapTransactionCancel(Model model) {
        // click Cancel User
        transactionService.pushNewLoginToTransfer(model);
        model.addAttribute("rows", transactionService.LIST_TRANSACTION);
        return "/transfer";
    }

    @GetMapping("/transaction/{id}")
    public String getListTransaction(@PathVariable("id") int userId) {
        return transactionService.getListTransaction(userId);
    }




}
