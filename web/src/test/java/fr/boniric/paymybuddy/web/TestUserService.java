package fr.boniric.paymybuddy.web;

import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserService {

    @Autowired
    UserService userService;

    @Test   // Api must is Up
    @Order(1)
    public void createNewUserTest() {

        // Given
        User user = new User();
        user.setFirstname("jon");
        user.setLastname("smith");
        user.setPassword("Test12345!!");
        user.setAddress("15 th Street");
        user.setZip("04507");
        user.setCity("Boston");
        user.setPhone(17043522);
        user.setEmail("test@test.fr");
        user.setBalance(10);
        user.setIban("IBAN");
        user.setSwift("SWIFT");
        user.setRoles("user");

        // When
        userService.saveUser(user);
        User userSearch = userService.getUserByEmail("test@test.fr");

        // Then
        Assertions.assertEquals(user.getFirstname(), userSearch.getFirstname());
        Assertions.assertEquals(user.getLastname(), userSearch.getLastname());
        Assertions.assertEquals(user.getAddress(), userSearch.getAddress());
        Assertions.assertEquals(user.getZip(), userSearch.getZip());
        Assertions.assertEquals(user.getCity(), userSearch.getCity());
        Assertions.assertEquals(user.getPhone(), userSearch.getPhone());
        Assertions.assertEquals(user.getBalance(), userSearch.getBalance());
        Assertions.assertEquals(user.getIban(), userSearch.getIban());
        Assertions.assertEquals(user.getSwift(), userSearch.getSwift());
        Assertions.assertEquals(user.getRoles(), userSearch.getRoles());

    }

    @Test  // Api must is Up
    @Order(2)
    public void updateBalanceUserTest() {

        // Given
        User userSearch = userService.getUserByEmail("test@test.fr");

        // When
        userService.updateUser(userSearch.getId(), 50.25);
        User userResult = userService.getUserByEmail("test@test.fr");

        // Then
        Assertions.assertEquals(50.25, userResult.getBalance());
        userService.updateUser(userResult.getId(), 10);
    }


}
