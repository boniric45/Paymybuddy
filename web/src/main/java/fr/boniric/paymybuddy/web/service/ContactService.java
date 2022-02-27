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

    public String addContact(User newUserReceiver, Model model) {
        String status = "";

        //User Authenticate
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmailAuthenticate = auth.getName();
        model.addAttribute("userEmail", userEmailAuthenticate);
        User newBuddy = userService.getUserByEmail(newUserReceiver.getEmail());

        //récupération id if usercontact is présent
        if (newBuddy == null) {
            status = "Contact unknown";
        } else {

            User userAuthenticated = userService.getUserByEmail(userEmailAuthenticate);
            int idUserAuthenticate = userAuthenticated.getId(); // initialize id user authenticate
            int idNewContactReceiver = newBuddy.getId(); // initialize id contact write
            Contact contact = new Contact(idUserAuthenticate, idNewContactReceiver); // initialize a new contact

            // Control contact is présent
            Contact resultContact = contactProxy.getControlContactById(idUserAuthenticate, idNewContactReceiver);
            if (resultContact.getContactId() == null) {
                status = "";
                contactProxy.saveContact(contact);
                status = "Contact saved";
            } else {
                status = "";
                status = "Contact is already present";
            }
        }
        return status;
    }

    public String listOfContact(int userAuthId) {
        return contactProxy.listContact(userAuthId);
    }

    public Contact findById(int userId) {
        return contactProxy.getContactById(userId);
    }

}
