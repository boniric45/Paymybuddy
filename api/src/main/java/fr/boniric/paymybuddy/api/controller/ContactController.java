package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    /**
     * Create - Add a new contact
     *
     * @return The association contact saved
     */
    @PostMapping("/contact")
    public Contact saveContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return contact;
    }

    /**
     * Create - Add a new list of contact
     *
     * @return The association contact saved
     */
    @GetMapping("/contact/{userAuthId}")
    public List<String> readContact(@PathVariable("userAuthId") Integer userIdWrite, Model model) {
        model.addAttribute("listContact", contactService.getListContact(userIdWrite));
        return contactService.getListContact(userIdWrite);
    }

    @GetMapping("/contact/all")
    public Iterable<Contact> getAllContact() {
        return contactService.getAllContact();
    }

}
