package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.ContactProxy;
import fr.boniric.paymybuddy.web.service.ContactService;
import fr.boniric.paymybuddy.web.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class UserController {

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactProxy contactProxy;

    @GetMapping("/")
    public String home() {
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/inscription")
    public String inscriptionPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/inscription";
    }

    @PostMapping(value = "/user")
    public ModelAndView saveUser(User user) {
        //Encode Password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        //save user
        userService.saveUser(user);

        // Save contact
        User resultUser = userService.getUserByEmail(user.getEmail());
        Contact contact = new Contact(resultUser.getId(), resultUser.getId());
        contactProxy.saveContact(contact);

        return new ModelAndView("redirect:/recapRegister");
    }

    @GetMapping("/bad")
    public String badPage() {
        return "/bad";
    }

    @GetMapping("/recapRegister")
    public String recapRegister(Model model) {

        // Data Saved
        model.addAttribute("varInfo", "Your registration has been registered an email link will be sent to you");
        return "/recapRegister";
    }

    @GetMapping("/users/{id}")
    public Iterable<User> getUserFindById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/updateBalance/{id}/{amountTransaction}")
    public void updateUser(@PathVariable("id") int updateUserId, @PathVariable("amountTransaction") double amountTransaction) {

        if (updateUserId != 0 || amountTransaction != 0) {
            Iterable<User> userId = userService.getUserById(updateUserId);
            for (User userIT : userId) {
                if (userIT != null) {
                    double amountTransactionRound = Math.round(amountTransaction * 100.0) / 100.0;
                    userIT.setBalance(amountTransactionRound);
                    userService.saveUser(userIT);
                }
            }
        }
    }
}

