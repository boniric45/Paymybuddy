package fr.boniric.paymybuddy.web.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ContactService {

    @Autowired
    ContactProxy contactProxy;

    @Autowired
    UserService userService;

    public String addContact(User newUser, Model model) {
        String status = "";

        //User Authenticate
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthenticate = auth.getName();
        model.addAttribute("userEmail", userAuthenticate);
        String emailNewUser = newUser.getEmail();
        User userContact = userService.getUserByEmail(emailNewUser);

        //récupération id if usercontact is présent
        if (userContact == null) {
            status = "Contact unknow";
        } else {
            User userAuth = userService.getUserByEmail(userAuthenticate);
            int idUserAuthenticate = userAuth.getId(); // initialize id user authenticate
            int idNewContact = userContact.getId(); // initialize id contact write
            Contact contact = new Contact(idNewContact,idUserAuthenticate); // initialize a new contact

                contactProxy.saveContact(contact);
                status = "Contact saved";
        }
        return status;
    }


    public String listOfContact(int userAuthId) {
        return contactProxy.listContact(userAuthId);
    }
}
