package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUserRepository {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateUserRepository() {

        // Given
        User user = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userRepository.save(user);

        // Then
        assertThat(user.getId()).isGreaterThan(0);

        userRepository.deleteById(user.getId());
    }

    @Test
    public void testGetAllUserRepository() {
        // Given
        User user = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        //When
        userRepository.save(user);
        Iterable<User> userIterable = userRepository.findAll();

        // Then
        assertNotNull(userIterable);

        userRepository.deleteById(user.getId());
    }

    @Test
    public void testGetUserByEmailRepository() {
        // Given
        User user = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        // When
        userRepository.save(user);
        User userResult = userRepository.findByEmail("test@test.fr").get();

        // Then
        assertNotNull(userResult);
        Assertions.assertEquals(user.getId(), userResult.getId());

        userRepository.deleteById(user.getId());

    }

    @Test
    public void testUpdateBalanceUserRepository() {
        // Given
        User user = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        // When
        userRepository.save(user);
        User userResult = userRepository.findByEmail("test@test.fr").get();
        userResult.setBalance(150);
        userRepository.save(userResult);
        User userBalanceUpdated = userRepository.findByEmail("test@test.fr").get();

        // Then
        Assertions.assertEquals(user.getBalance(), 0);
        Assertions.assertEquals(userBalanceUpdated.getBalance(), 150);

        userRepository.deleteById(userBalanceUpdated.getId());

    }

    @Test
    public void testDeleteUserByIdRepository() {

        // Given
        User user = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        // When
        userRepository.save(user);
        User userResult = userRepository.findByEmail("test@test.fr").get();

        assertEquals("test@test.fr", userResult.getEmail());
        userRepository.deleteById(userResult.getId());

        // Then
        assertFalse(userRepository.findByEmail("test@test.fr").isPresent());

    }




}
