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

    /**
     * @param authUserId
     * @return List Contact Connexion of Authenticate User
     */
    public List<String> getListContact(int authUserId) {
        List<String> listContact = new ArrayList<>();
        System.out.println(contactRepository.findAll());
        Iterable<Contact> userAllContact = contactRepository.findAll();
        for (Contact contact : userAllContact) {
            if (contact.getUsersId() == authUserId) {
                Iterable<User> user = userService.getUserById(contact.getBuddyId());
                for (User result : user) {
                    listContact.add(result.getFirstname() + " " + result.getLastname() + ": " + result.getEmail());
                }
            }
        }
        return listContact;
    }

    /**
     * Get Contact with userId and buddyId
     *
     * @param userId,buddyId
     * @return Contact
     */
    public Contact getControlContact(int userId, int buddyId) {
        Contact resultContact = new Contact();
        Iterable<Contact> contactIterable = contactRepository.findAll();
        for (Contact contact : contactIterable) {
            if (contact.getUsersId() == userId && contact.getBuddyId() == buddyId) {
                resultContact = contact;
            }
        }
        return resultContact;
    }

    /**
     * Get Contact by userId
     *
     * @param userId
     * @return
     */
    public Optional<Contact> getContactByUserId(int userId) {
        return contactRepository.findById(userId);
    }

    /**
     * Get all contact
     *
     * @return Iterable Contact
     */
    public Iterable<Contact> getAllContact() {
        return contactRepository.findAll();
    }

    /**
     * Save Contact with CRUD Method
     */
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    /**
     * Delete Contact with CRUD Method
     */
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

}

