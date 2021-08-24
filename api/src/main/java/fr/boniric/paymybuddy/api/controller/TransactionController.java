package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * Create - Add a new Transaction
     *
     * @param transaction An object transaction
     * @return The transaction object saved
     */
    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    /**
     * Read - Get one transaction
     * @param id The id of the transaction
     * @return An transaction object full filled
     */
    @GetMapping("/transaction/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if(transaction.isPresent()) {
            return transaction.get();
        } else {
            return null;
        }
    }

}
