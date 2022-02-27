package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    /**
     * Create - Add a new contact
     *
     * @return The association contact saved
     */
    @PostMapping("/contact")
    public void saveContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
    }

    /**
     * Get List of Contact
     *
     * @return List
     */
    @GetMapping("/contact/{userAuthId}/list")
    public List<String> readContact(@PathVariable("userAuthId") int userId, Model model) {
        model.addAttribute("listContact", contactService.getListContact(userId));
        return contactService.getListContact(userId);
    }

    /**
     * Get Contact by id
     *
     * @return Contact
     */
    @GetMapping("/contact/{userId}/{userReceiverId}")
    public Contact readContactById(@PathVariable("userId") int userId, @PathVariable("userReceiverId") int userReceiverId) {
        return contactService.getControlContact(userId, userReceiverId);
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
     */
    @DeleteMapping("/contact/{id}")
    public void deleteContactById(@PathVariable("id") int id) {
        Contact contact = contactService.getContactByUserId(id).get();
        contactService.delete(contact);
    }


}
