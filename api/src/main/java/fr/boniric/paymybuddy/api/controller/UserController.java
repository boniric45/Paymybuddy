package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create - Add a new User
     *
     * @param user An object user
     * @return The user object saved
     */
    @PostMapping("/user")
    public User register(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            System.out.println(" Email déjà en base ");
            return null;
        } else {
            userService.saveUser(user);
        }
        return user;
    }

    /**
     * Read - Get all user
     *
     * @return All User object full filled
     */
    @GetMapping("/user/all")
    public Iterable<User> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * Read - Get one user by Email
     *
     * @param email The email of the user
     * @return An User object full filled
     */
    @GetMapping("/user/{email}")
    public Optional<User> getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/{id}")
    public Iterable<User> getUserFindById(@PathVariable("id") long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Update balance User with amountTransaction
     *
     * @param id                The id of the user
     * @param amountTransaction new balance
     *                          Update balance User with amountTransaction
     */
    @PutMapping("/updateBalance/{id}/{amountTransaction}")
    public void updateUser(@PathVariable("id") Integer id, @PathVariable("amountTransaction") double amountTransaction) {
        Iterable<User> userId = userService.getUserById(id);
        if (userId != null) {
            for (User userIT : userId) {
                userIT.setBalance(amountTransaction);
                System.out.println(userIT);
                userService.saveUser(userIT);
            }
        }
    }

    /**
     * Delete User By Id
     *
     * @param id The id of the user
     */
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
    }
}







