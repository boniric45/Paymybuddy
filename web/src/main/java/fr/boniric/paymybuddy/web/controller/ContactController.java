package fr.boniric.paymybuddy.web.controller;

import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/addconnection")
    public String addconnection(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthenticate = auth.getName();
        model.addAttribute("userEmail", userAuthenticate); // push Email user authenticate in forms addconnection
        User user = new User();
        model.addAttribute("user", user); // push Email contact in forms addconnection
        return "/addconnection";
    }

    @GetMapping("/contact/{id}")
    public Contact getContactById(@PathVariable("id") int userId){
        return contactService.findById(userId);
    }

    @PostMapping("/addconnection")
    public String addConnectionPost(User newUser, Model model) {
        String status = contactService.addContact(newUser, model);
        model.addAttribute("status", status);
        return "/addconnection";
    }


}
