package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.repository.TransactionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Iterable<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public List<String> getAllTransactionById(int idUser) {
        List<String> transactionList = new ArrayList<>();
        Iterable<Transaction> transactions = getAllTransaction();

        //Search transaction for user ID
        for (Transaction tr : transactions) {
            if (tr.getUserId() == idUser) {

                transactionRepository.findAllById(Collections.singleton(idUser));
                transactionList.add(tr.getListEmail() + " - " + tr.getTransactionAmount() + " €");
            }

        }

        return transactionList;
    }
}
