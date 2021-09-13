package fr.boniric.paymybuddy.web.controller;


import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.service.ContactService;
import fr.boniric.paymybuddy.web.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class UserController {

    private String EMAILUSER_AUTHENTICATE;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String home() {
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    //Create
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
        return new ModelAndView("redirect:/recapRegister");
    }


    @GetMapping("/bad")
    public String badPage() {
        return "/bad";
    }

    @GetMapping("/recapRegister")
    public String recapRegister(Model model) {
        //TODO a finir

        // Data Saved
        model.addAttribute("varInfo", "Your registration has been registered an email link will be sent to you");
        return "/recapRegister";
    }


}
