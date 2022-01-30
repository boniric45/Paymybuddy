package fr.boniric.paymybuddy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

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
    public void testAddNewUser() throws Exception {

        User user = new User(null, "Test12345!!", "jon", "john", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        User userResult = userService.getUserByEmail("test@test.fr").get();
        userService.deleteUserById(userResult.getId());
    }

    @Test
    public void testGetAllUser() throws Exception {

        // Given
        User user = new User(null, "Test12345..", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        // When
        userService.saveUser(user);
        List<Iterable<User>> iterableList = Collections.singletonList(userService.getAllUser());

        // Then
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jon")));

        Assertions.assertNotNull(iterableList);

        User userResult = userService.getUserByEmail("test@test.fr").get();
        userService.deleteUserById(userResult.getId());
    }

    @Test
    public void testGetUserByEmail() throws Exception {

        // Given
        User user = new User(null, "Test12345..", "jon", "jon", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        List<Iterable<User>> iterableList = Collections.singletonList(userService.getAllUser());

        // When
        userService.saveUser(user);

        // Then
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jon")));

        Assertions.assertNotNull(iterableList);

        User userResult = userService.getUserByEmail("test@test.fr").get();
        userService.deleteUserById(userResult.getId());
    }

    @Test
    public void testGetUserById() throws Exception {
        // Given
        User user = new User(null, "Test12345!!", "jon", "john", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        // When
        userService.saveUser(user);
        long userId = userService.getUserByEmail("test@test.fr").get().getId();

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jon")))
                .andExpect(content().string(containsString("10 th Street")))
                .andExpect(content().string(containsString("New York")));

        User userResult = userService.getUserByEmail("test@test.fr").get();
        userService.deleteUserById(userResult.getId());
    }

    @Test
    public void testUpdateBalanceUser() throws Exception {
        User user = new User(null, "Test12345!!", "jon", "john", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");

        userService.saveUser(user);
        long userId = userService.getUserByEmail("test@test.fr").get().getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/updateBalance/{id}/{amountTransaction}", userId, 20)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        User user2 = userService.getUserByEmail("test@test.fr").get();
        Assertions.assertEquals(user2.getBalance(), 20);
        User userResult = userService.getUserByEmail("test@test.fr").get();
        userService.deleteUserById(userResult.getId());
    }

    @Test
    public void testDeleteUserById() throws Exception {

        User user = new User(null, "Test12345!!", "jon", "john", "10 th Street", "04508", "New York", "0102020202", "test@test.fr", 0, "IBAN", "SWIFT", "user");
        userService.saveUser(user);

        User userResult = userService.getUserByEmail("test@test.fr").get();

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUser/{id}", userResult.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
}
