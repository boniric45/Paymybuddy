package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestContactRepository {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    ContactRepository contactRepository;

    @AfterEach
    public void init() {
        User userResult = userService.getUserByEmail("test@test.fr").get();
        User userResult2 = userService.getUserByEmail("test2@test.fr").get();
        userService.delete(userResult);
        userService.delete(userResult2);
    }

    @Test
    public void testCreateContactRepository() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());
        Contact contact = new Contact(1, user1Id, user2Id);


        // When
        contactRepository.save(contact);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        // Then
        assertThat(contact.getContactId()).isGreaterThan(0);

        contactRepository.delete(contactResult);
    }

    @Test
    public void testGetAllContactRepository() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());
        Contact contact = new Contact(1, user1Id, user2Id);

        // When
        contactRepository.save(contact);
        List<Contact> contactList = (List<Contact>) contactRepository.findAll();

        // Then
        assertThat(contactList.size()).isGreaterThan(0);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        contactRepository.delete(contactResult);
    }

    @Test
    public void testGetAllContactRepositoryById() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());
        Contact contact = new Contact(1, user1Id, user2Id);

        // When
        contactRepository.save(contact);
        Contact contactSearch = contactService.getControlContact(user1Id, user2Id);
        Optional<Contact> contactResult = contactRepository.findById(contactSearch.getContactId());

        // Then
        assertThat(contactResult.get().getContactId()).isGreaterThan(1);

        contactRepository.delete(contactSearch);
    }

    @Test
    public void testDeleteContactRepository() {

        // Given
        User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user1);
        userService.saveUser(user2);

        int user1Id = Math.toIntExact(userService.getUserByEmail("test@test.fr").get().getId());
        int user2Id = Math.toIntExact(userService.getUserByEmail("test2@test.fr").get().getId());
        Contact contact = new Contact(1, user1Id, user2Id);

        // When
        contactRepository.save(contact);
        Contact contactResult = contactService.getControlContact(user1Id, user2Id);
        contactRepository.delete(contactResult);

        // Then
        Assertions.assertThat(contact.getContactId()).isGreaterThan(0);
    }


}
