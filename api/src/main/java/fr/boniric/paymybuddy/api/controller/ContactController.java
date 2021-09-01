package fr.boniric.paymybuddy.api.controller;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;


    /**
     * Create - Add a new contact
     *
     *
     * @return The association contact saved
     */
    @PostMapping("/contact/{userAuthId}/{contactId}")
    public Contact saveContact(@PathVariable("contactId") int contactId,@PathVariable("userId") int userId, @RequestBody Contact contact) {
        Contact contact1 = new Contact();

        System.out.println(contact);
        System.out.println(contactId+" "+userId);
        return contactService.saveId(contact1);
    }



    @GetMapping("/contact/all")
    public Iterable<Contact> getAllContact() {
        return contactService.getAllContact();
    }

}
