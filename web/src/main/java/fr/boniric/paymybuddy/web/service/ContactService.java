package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.repository.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactProxy contactProxy;

    public void saveContact(Contact contact) {  //TODO faire le controle de cette rubrique
        contactProxy.saveContact(contact);
    }

    public String listOfContact(int userAuthId) {
        return contactProxy.listContact(userAuthId);
    }
}
