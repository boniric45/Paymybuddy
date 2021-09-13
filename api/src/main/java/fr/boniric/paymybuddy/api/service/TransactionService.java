package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.repository.TransactionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) { transactionRepository.save(transaction); }

    public Iterable<Transaction> getAllTransaction() {return transactionRepository.findAll(); }

    public Optional<Transaction> getTransactionById(Long id) { return transactionRepository.findById(id);}
}
