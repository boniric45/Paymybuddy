package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestTransactionRepository {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @Test
    public void testCreateTransactionRepository(){

        // Given
        LocalDateTime date = LocalDateTime.now();
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When Save User
        userRepository.save(user1);
        userRepository.save(user2);

        int user1Id = Math.toIntExact(userRepository.findByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userRepository.findByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(user2Id, user1Id);
        contactRepository.save(contact);

        Transaction transaction = new Transaction(null, 2, user1Id, user2Id, date, 10.20, 2.0, 12.20, user1.getEmail(), "payment habbits");
        transactionRepository.save(transaction);

        Assertions.assertEquals(10.20, transaction.getTransactionAmount());
        Assertions.assertEquals(2.0, transaction.getTransactionCommissionAmount());
        Assertions.assertEquals(12.20, transaction.getTransactionTotalAmount());

        // Delete Transaction, Contact, Users
        Iterable<Transaction> transactionIterable = transactionRepository.findAll();
        for (Transaction transaction1 : transactionIterable) {
            if (transaction1.getUserId() == user1Id) {
                transactionRepository.deleteById(transaction1.getTransactionId());
                contactRepository.delete(contact);
                userRepository.delete(user1);
                userRepository.delete(user2);
            }
        }
    }

    @Test
    public void testGetTransactionRepository(){

        // Given
        LocalDateTime date = LocalDateTime.now();
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "smith", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When Save User
        userRepository.save(user1);
        userRepository.save(user2);

        int user1Id = Math.toIntExact(userRepository.findByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userRepository.findByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(user2Id, user1Id);
        contactRepository.save(contact);

        Transaction transaction = new Transaction(null, 2, user1Id, user2Id, date, 10.20, 2.0, 12.20, user1.getEmail(), "payment habbits");
        transactionRepository.save(transaction);

        Assertions.assertEquals(10.20, transaction.getTransactionAmount());
        Assertions.assertEquals(2.0, transaction.getTransactionCommissionAmount());
        Assertions.assertEquals(12.20, transaction.getTransactionTotalAmount());
        Assertions.assertEquals(2, transaction.getPaymentTypeId());

        // Delete Transaction, Contact, Users
        Iterable<Transaction> transactionIterable = transactionRepository.findAll();
        for (Transaction transaction1 : transactionIterable) {
            if (transaction1.getUserId() == user1Id) {
                transactionRepository.deleteById(transaction1.getTransactionId());
                contactRepository.delete(contact);
                userRepository.delete(user1);
                userRepository.delete(user2);
            }
        }


    }
    }



