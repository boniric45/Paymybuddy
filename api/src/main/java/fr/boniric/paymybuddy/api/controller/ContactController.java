package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

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
     * Get List of Contact
     *
     * @return List
     */
    @GetMapping("/contact/{userAuthId}")
    public List<String> readContact(@PathVariable("userAuthId") Integer userIdWrite, Model model) {
        model.addAttribute("listContact", contactService.getListContact(userIdWrite));
        return contactService.getListContact(userIdWrite);
    }

    /**
     * Get All Contact
     *
     * @return Contact
     */
    @GetMapping("/contact/all")
    public Iterable<Contact> getAllContact() {
        return contactService.getAllContact();
    }

    /**
     * delete Contact by Id
     *
     */
    @DeleteMapping("/deleteContact/{id}")
    public void deleteContactById(@PathVariable("id") int id){
        Contact contact = contactService.getContactByUserId(id).get();
            contactService.delete(contact);
    }

    //TODO affichage table ko et Actualisation page après transaction

}
