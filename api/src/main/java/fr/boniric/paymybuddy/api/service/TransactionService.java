package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Iterable<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public List<String> getAllTransactionById(int idUser) {
        List<String> transactionList = new ArrayList<>();
        Optional<Contact> contact = contactService.getContactByUserId(idUser);
        Iterable<Transaction> transactions = getAllTransaction();
        Iterable<User> user = userService.getUserById(idUser);

        if (contact.isPresent()) {
            for (Transaction transactionIterable : transactions) {
                int transactionAmount = (int) transactionIterable.getTransactionAmount(); // parse cast int
                for (User userIterable : user) {
                    transactionList.add(userIterable.getFirstname() + "-" + transactionIterable.getDescription() + "-" + transactionAmount + " €");
                }
            }
        }
        return transactionList;

    }


    public void deleteTransactionByUserId(int userId) {
        transactionRepository.deleteById(userId);
    }
}
