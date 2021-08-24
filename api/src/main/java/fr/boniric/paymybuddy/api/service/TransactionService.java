package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
        //TODO reste à finir
    }

    public Iterable<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
        //TODO reste à finir
    }
}
