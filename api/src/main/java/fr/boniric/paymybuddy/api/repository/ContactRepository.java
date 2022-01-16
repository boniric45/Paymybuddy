package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

}
