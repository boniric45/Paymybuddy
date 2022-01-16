package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestUserRepository {

    @Autowired
    UserRepository userRepository;


@Test
    public void testCreateUserRepository(){

    // Given
    User user1 = new User(null, "Test12345!!", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
    User user2 = new User(null, "Test12345!!", "jeanne", "jon", "10 th Street", "04508", "New York", "0102020202", "test2@test.fr", 0, "IBAN", "SWIFT", "user");

    //When
    userRepository.save(user1);
    userRepository.save(user2);

    // Then
    assertThat(user1.getId()).isGreaterThan(0);
    assertThat(user2.getId()).isGreaterThan(0);

    userRepository.deleteById(user1.getId());
    userRepository.deleteById(user2.getId());

}
}
