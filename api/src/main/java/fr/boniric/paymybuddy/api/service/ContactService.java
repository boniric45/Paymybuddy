package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.Contact;
import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.repository.ContactRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserService userService;


    public List<String> getListContact(int authUserId){
    List<String> listContact = new ArrayList<>();
    Iterable<Contact> userContact = getAllContact();
    Iterable<User> userFirstname = userService.getAllUser();
    for (Contact contact : userContact){
        if (contact.getUsersId().equals(authUserId)){
            for (User user : userFirstname){
                if (user.getId().equals(contact.getContactId())){
                   listContact.add(user.getFirstname()+" "+user.getLastname()+": "+user.getEmail());
              }
            }
        }
    }
    return listContact;
}

public Optional<Contact> getContactByUserId(int userId){
        return contactRepository.findById(userId);
}

    public Iterable<Contact> getAllContact() { return contactRepository.findAll(); }

    public void saveContact(Contact contact) { contactRepository.save(contact); }

    public void delete(Contact contact) {contactRepository.delete(contact); }
}

