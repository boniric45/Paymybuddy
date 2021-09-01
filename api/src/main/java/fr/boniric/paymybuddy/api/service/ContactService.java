package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.repository.ContactRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public Iterable<Contact> getAllContact() {
        return contactRepository.findAll();
    }

    public Contact saveId(Contact contact) {
        return contactRepository.save(contact);
    }


}
