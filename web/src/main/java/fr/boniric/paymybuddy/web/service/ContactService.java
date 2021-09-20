package fr.boniric.paymybuddy.web.service;

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

    public void saveContact(Contact contact) {
        contactProxy.saveContact(contact);
    }

    public String addContact(User newUser, Model model) {
        String status = "";
        //User Authenticate
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthenticate = auth.getName();
        model.addAttribute("userEmail", userAuthenticate);
        User userContact = userService.getUserByEmail(newUser.getEmail());

        //récupération id if usercontact is présent
        if (userContact == null) {
            status = "Contact unknow";

        } else {
            User userAuth = userService.getUserByEmail(userAuthenticate);

            int idUserAuthenticate = userAuth.getId(); // initialize id user authenticate
            int idNewContact = userContact.getId(); // initialize id contact write
            Contact contact = new Contact(); // initialize a new contact

            contact.setContactId(idNewContact);
            contact.setUsersId(idUserAuthenticate);

            //TODO vérifier si le contact est déja présent
            saveContact(contact); // save a contact
            status = "Contact saved";
        }
        return status;
    }

    public String listOfContact(int userAuthId) {
        return contactProxy.listContact(userAuthId);
    }
}
