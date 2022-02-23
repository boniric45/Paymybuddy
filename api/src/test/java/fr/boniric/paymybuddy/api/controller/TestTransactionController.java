package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.Transaction;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.TransactionService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.springframework.http.MediaType.APPLICATION_JSON;

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


    @Test
    public void createTransactionTest() throws Exception {

        // Given
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Paris"));
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        // When Save User
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(1, user1Id, user2Id);
        contactService.saveContact(contact);

        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        Transaction transaction = new Transaction(1, 2, user1Id, user2Id, date, 10.20, 2.0, 12.20, "test@test.fr", "payment habbits");
        transactionService.saveTransaction(transaction);

        //  Then Save Transaction
        mockMvc.perform(MockMvcRequestBuilders
                .post("/transaction")
                .content(String.valueOf(transaction))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

        Iterable<Transaction> transactionIterable = transactionService.getAllTransaction();
        Transaction transactionResult = new Transaction();
        for (Transaction transactionObj : transactionIterable) {
            if (transactionObj.getUserId() == user1Id && transactionObj.getUserReceiverId() == user2Id) {
                transactionResult = transactionObj;

            }
        }
        Assertions.assertEquals(transactionResult.getUserId(), user1Id);
        Assertions.assertEquals(transactionResult.getUserReceiverId(), user2Id);
        Assertions.assertEquals(transactionResult.getDescription(), "payment habbits");
        Assertions.assertEquals(transactionResult.getListContact(), "test@test.fr");
        Assertions.assertEquals(transactionResult.getTransactionAmount(), 10.20);
        Assertions.assertEquals(transactionResult.getTransactionCommissionAmount(), 2);
        Assertions.assertEquals(transactionResult.getTransactionTotalAmount(), 12.20);

        // Delete Transaction, Contact, Users
        for (Transaction transactionDelete : transactionIterable) {
            if (transactionDelete.getUserId() == user1Id) {
                transactionService.deleteTransactionByUserId(transactionDelete.getTransactionId());
                contactService.delete(contactResult);
                userService.delete(user1);
                userService.delete(user2);
            }
        }

    }


}
