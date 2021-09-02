package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

        if (userService.getUserByEmail(user.getEmail()).isPresent()){
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


//    /**
//     * Update - Update an existing user
//     *
//     * @param id   - The id of the user to update
//     * @param user - The user object updated
//     */
//    @PutMapping("/user/{id}")
//    public User updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
//        Optional<User> u = userService.getUserById(id);
//        if (u.isPresent()) {
//            User currentUser = u.get();
//
//            String password = user.getPassword();
//            if (password != null) {
//                currentUser.setPassword(password);
//            }
//
//            String address = user.getAddress();
//            if (address != null) {
//                currentUser.setAddress(address);
//            }
//
//            String zip = user.getZip();
//            if (zip != null) {
//                currentUser.setZip(zip);
//            }
//
//            String city = user.getCity();
//            if (city != null) {
//                currentUser.setCity(city);
//            }
//
//            String phone = user.getPhone();
//            if (phone != null) {
//                currentUser.setPhone(phone);
//            }
//
//            String iban = user.getIban();
//            if (iban != null) {
//                currentUser.setIban(iban);
//            }
//
//            String swift = user.getSwift();
//            if (swift != null) {
//                currentUser.setSwift(swift);
//            }
//            userService.saveUser(currentUser);
//            return currentUser;
//        } else {
//            return null;
//        }
//    }
//
//
//    /**
//     * Delete - Delete an user
//     *
//     * @param id - The id of the user to delete
//     */
//    @DeleteMapping("/user/{id}")
//    public void deleteUser(@PathVariable("id") Integer id) {
//        userService.deleteUser(id);
//    }


}
