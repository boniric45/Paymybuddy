package fr.boniric.paymybuddy.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
public class TestIntegrationTransaction {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSerializingDateWithJackson_thenSerializedToTimestamp()
            throws JsonProcessingException, ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = df.parse("01-01-1970 01:00");

        Event event = new Event(1, 1, date);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(event);
    }

    @Test
    public void testIntegrationTransaction() throws Exception {

        // Given
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Paris"));
        User user1 = new User(1L, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(2L, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        // When Save User
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        // When Save Contact
        Contact contact = new Contact(1, user1Id, user2Id);
        contactService.saveContact(contact);

        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setPaymentTypeId(2);
        transaction.setUserId(user1Id);
        transaction.setUserReceiverId(user2Id);
        transaction.setDate(date);
        transaction.setTransactionAmount(10.20);
        transaction.setTransactionCommissionAmount(2.0);
        transaction.setTransactionTotalAmount(12.20);
        transaction.setListContact("Test@test.fr");
        transaction.setDescription("payment habbits");
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
        Assertions.assertEquals(transactionResult.getListContact(), "Test@test.fr");
        Assertions.assertEquals(transactionResult.getTransactionAmount(), 10.20);
        Assertions.assertEquals(transactionResult.getTransactionCommissionAmount(), 2);
        Assertions.assertEquals(transactionResult.getTransactionTotalAmount(), 12.20);

        // Delete Transaction, Contact, Users
        for (Transaction transactionDelete : transactionIterable) {
            if (transactionDelete.getUserId() == user1Id) {
                transactionService.deleteTransactionByUserId(transactionDelete.getTransactionId());
                contactService.delete(contactResult);
                userService.deleteUserById(user1Id);
                userService.deleteUserById(user2Id);
            }
        }

    }
}
