package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.web.repository.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactProxy contactProxy;

    public void saveId(int userAuthId, int newContactId) {
        contactProxy.saveContact(userAuthId, newContactId);
    }
}
