package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.service.ContactService;
import fr.boniric.paymybuddy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

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
    @GetMapping("/listcontact/{userAuthId}")
    public List<String> readContact(@PathVariable("userAuthId") int userId, Model model) {
        model.addAttribute("listContact", contactService.getListContact(userId));
        return contactService.getListContact(userId);
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
    @DeleteMapping("/deleteContact/{id}")
    public void deleteContactById(@PathVariable("id") int id) {
        Contact contact = contactService.getContactByUserId(id).get();
        contactService.delete(contact);
    }

    //TODO revoir contrainte base contact, control sur contact déjà présent, revoir page de payment date, merge le P8
    // Todo quand on paye avec account ne doit pas vider la table des transactions ou reactualiser la page
    //TODO voir également la page read me photo


}
