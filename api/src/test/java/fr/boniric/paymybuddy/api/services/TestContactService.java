package fr.boniric.paymybuddy.api.services;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class TestContactService {

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Test
    public void testSaveContactService() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());

        Contact contact = new Contact(user1Id, user2Id);

        // When
        contactService.saveContact(contact);

        // Then
        assertEquals(contact.getContactId(), user1Id);

        contactService.delete(contact);
        userService.delete(user1);
        userService.delete(user2);

    }

    @Test
    public void testGetListContact() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());

        Contact contact = new Contact(user1Id, user2Id);

        // When
        contactService.saveContact(contact);
        List<String> contactList = contactService.getListContact(user1Id);

        // Then
        assertEquals(contact.getContactId(), user1Id);
        assertTrue(contactList.size() > 0);

        contactService.delete(contact);
        userService.delete(user1);
        userService.delete(user2);

    }

    @Test
    public void testGetContactByUserId() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());

        Contact contact = new Contact(user1Id, user2Id);

        // When
        contactService.saveContact(contact);
        Optional<Contact> contactOptional = contactService.getContactByUserId(user1Id);

        // Then
        assertEquals(user1Id, contactOptional.get().getContactId());
        assertEquals(user2Id, contactOptional.get().getUsersId());

        contactService.delete(contact);
        userService.delete(user1);
        userService.delete(user2);
    }

    @Test
    public void testGetAllContact() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());

        Contact contact = new Contact(user1Id, user2Id);
        List<String> contactList = new ArrayList<>();

        // When
        contactService.saveContact(contact);
        Iterable<Contact> contactIterable = contactService.getAllContact();
        for (Contact c : contactIterable) {
            contactList.add(String.valueOf(c.getContactId()));
        }

        // Then
        assertTrue(contactList.size() > 0);

        contactService.delete(contact);
        userService.delete(user1);
        userService.delete(user2);
    }

    @Test
    public void testDeleteContact() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());

        Contact contact = new Contact(user1Id, user2Id);
        List<String> contactList = new ArrayList<>();

        // When
        contactService.saveContact(contact);
        Iterable<Contact> contactIterable = contactService.getAllContact();
        for (Contact c : contactIterable) {
            contactList.add(String.valueOf(c.getContactId()));
        }
        contactService.delete(contact);

        // Then
        assertTrue(contactList.size() > 0);
        org.assertj.core.api.Assertions.assertThat(contact.getContactId()).isGreaterThan(0);


        userService.delete(user1);
        userService.delete(user2);
    }

}
