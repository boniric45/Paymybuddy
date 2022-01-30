package fr.boniric.paymybuddy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestContactController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @AfterEach
    public void init() {
        User userResult = userService.getUserByEmail("test@test.fr").get();
        User userResult2 = userService.getUserByEmail("test2@test.fr").get();
        userService.delete(userResult);
        userService.delete(userResult2);
    }

    @Test
    @Order(1)
    public void testCreateContact() throws Exception {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");
        String url = "http://localhost:9002/contact";

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());
        Contact contact = new Contact(1, user1Id, user2Id, "jon");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(contact);

        //Then
        mockMvc.perform(post(url)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());

        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        contactService.delete(contactResult);
    }

    @Test
    @Order(2)
    public void testGetListContactById() throws Exception {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        Contact contact = new Contact(1, user1Id, user2Id, "jon");
        contactService.saveContact(contact);

        Contact contactResult = contactService.getControlContact(user1Id, user2Id);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/listcontact/{id}", contactResult.getContactId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(user2Id, contactResult.getBuddyId());
        contactService.delete(contactResult);
    }

    @Test
    @Order(3)
    public void testGetListAllContact() throws Exception {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        Contact contact = new Contact(1, user1Id, user2Id, "jon");
        contactService.saveContact(contact);

        Iterable<Contact> contactResult = contactService.getAllContact();

        List<Contact> contactList = new ArrayList<>();
        for (Contact i : contactResult) {
            contactList.add(i);
        }

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(contactList.size() > 0);

        Contact contactTest = contactService.getControlContact(user1Id, user2Id);
        contactService.delete(contactTest);
    }

    @Test
    @Order(4)
    public void testDeleteContactById() throws Exception {
        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());

        Contact contact = new Contact(null, user1Id, user2Id, "jon");
        contactService.saveContact(contact);

        Iterable<Contact> contactResult = contactService.getAllContact();
        int contactId = 0;
        for (Contact i : contactResult) {
            if (user2Id == i.getContactId()) {
                contactId = i.getContactId();
            }
        }

        Contact contactTest = contactService.getControlContact(user1Id, user2Id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteContact/{id}", contactTest.getContactId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactId").doesNotExist());

    }

}

