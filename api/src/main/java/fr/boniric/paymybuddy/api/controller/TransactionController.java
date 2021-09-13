package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Create - Add a new Transaction
     *
     * @param transaction An object transaction
     * @return The transaction object saved
     */
    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        System.out.println(transaction);
         transactionService.saveTransaction(transaction);
         return transaction;
    }

    /**
     * Read - Get one transaction
     *
     * @param id The id of the transaction
     * @return An transaction object full filled
     */
    @GetMapping("/transaction/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id);
    }


    @GetMapping("/transaction/all")
    public Iterable<Transaction> getAllTransaction() {
        return transactionService.getAllTransaction();
    }
}
