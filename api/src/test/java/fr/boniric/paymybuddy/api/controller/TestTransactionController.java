package fr.boniric.paymybuddy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.TransactionService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateTransaction() throws Exception {

        // Given
        LocalDateTime date = LocalDateTime.now();
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When Save User
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(user2Id, user1Id);
        contactService.saveContact(contact);

        Transaction transaction = new Transaction(null, 2, user1Id, user2Id, date, 10.20, 2.0, 12.20, user1.getEmail(), "payment habbits");

        //  Then Save Transaction
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").exists());

        // Delete Transaction, Contact, Users
        Iterable<Transaction> transactionIterable = transactionService.getAllTransaction();
        for (Transaction transaction1 : transactionIterable) {
            if (transaction1.getUserId() == user1Id) {
                transactionService.deleteTransactionByUserId(transaction1.getTransactionId());
                contactService.delete(contact);
                userService.delete(user1);
                userService.delete(user2);
            }
        }
    }

    @Test
    public void testGetTransaction() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When Save User
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(user2Id, user1Id);
        contactService.saveContact(contact);

        Transaction transaction = new Transaction(null, 2, user1Id, user2Id, date, 10.20, 2.0, 12.20, user1.getEmail(), "payment habbits");
        transactionService.saveTransaction(transaction);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/{id}", user1Id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jon")))
                .andExpect(content().string(containsString("10")))
                .andExpect(content().string(containsString("payment habbits")));

        // Delete Transaction, Contact, Users
        Iterable<Transaction> transactionIterable = transactionService.getAllTransaction();
        for (Transaction transaction1 : transactionIterable) {
            if (transaction1.getUserId() == user1Id) {
                transactionService.deleteTransactionByUserId(transaction1.getTransactionId());
                contactService.delete(contact);
                userService.delete(user1);
                userService.delete(user2);
            }
        }
    }
}
