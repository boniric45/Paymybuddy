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

        Contact contact = new Contact(1, user1Id, user2Id, "jon");

        // When
        contactService.saveContact(contact);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);

        // Then
        assertEquals(contact.getUsersId(), user1Id);
        assertEquals(contact.getBuddyId(), user2Id);

        contactService.delete(contactResult);
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

        Contact contact = new Contact(1, user1Id, user2Id, "jon");

        // When
        contactService.saveContact(contact);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        List<String> contactList = contactService.getListContact(user1Id);

        // Then
        assertEquals(contact.getUsersId(), user1Id);
        assertEquals(contact.getBuddyId(), user2Id);
        assertTrue(contactList.size() > 0);

        contactService.delete(contactResult);
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

        Contact contact = new Contact(1, user1Id, user2Id, "jon");

        // When
        contactService.saveContact(contact);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        Optional<Contact> contactOptional = contactService.getContactByUserId(contactResult.getContactId());

        // Then
        assertEquals(user1Id, contactOptional.get().getUsersId());
        assertEquals(user2Id, contactOptional.get().getBuddyId());

        contactService.delete(contactResult);
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

        Contact contact = new Contact(1, user1Id, user2Id, "jon");
        List<String> contactList = new ArrayList<>();

        // When
        contactService.saveContact(contact);
        Iterable<Contact> contactIterable = contactService.getAllContact();
        for (Contact c : contactIterable) {
            contactList.add(String.valueOf(c.getContactId()));
        }

        // Then
        assertTrue(contactList.size() > 0);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        contactService.delete(contactResult);
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

        Contact contact = new Contact(1, user1Id, user2Id, "jon");
        List<String> contactList = new ArrayList<>();

        // When
        contactService.saveContact(contact);
        Iterable<Contact> contactIterable = contactService.getAllContact();
        for (Contact c : contactIterable) {
            contactList.add(String.valueOf(c.getContactId()));
        }

        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        contactService.delete(contactResult);

        // Then
        assertTrue(contactList.size() > 0);
        org.assertj.core.api.Assertions.assertThat(contact.getContactId()).isGreaterThan(0);

        userService.delete(user1);
        userService.delete(user2);
    }

}
