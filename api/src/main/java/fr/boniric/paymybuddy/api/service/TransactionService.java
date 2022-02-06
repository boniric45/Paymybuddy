package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    /**
     * Save Transaction with CRUD Method
     *
     * @param transaction
     */
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Get All Transaction with CRUD Method
     *
     * @return Iterable<Transaction>
     */
    public Iterable<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    /**
     * Get All Transaction by User ID sender money
     *
     * @param idUser
     * @return List<String>
     */
    public List<String> getAllTransactionById(int idUser) {
        List<String> transactionList = new ArrayList<>();
        Iterable<Transaction> allTransaction = getAllTransaction(); // Get All Transaction

        for (Transaction transactionIterable : allTransaction) {
            if (idUser == transactionIterable.getUserId()) {
                Iterable<User> userReceiver = userService.getUserById(transactionIterable.getUserReceiverId());

                for (User userIterable : userReceiver) {
                    transactionList.add(userIterable.getFirstname() + "-" + transactionIterable.getDescription() + "-" + transactionIterable.getTransactionAmount() + " €");
                }
            }
        }
        return transactionList;
    }

    /**
     * Delete Transaction By User Id  with CRUD Method
     * Use with test
     *
     * @param userId
     */
    public void deleteTransactionByUserId(int userId) {
        transactionRepository.deleteById(userId);
    }

}

