package fr.boniric.paymybuddy.web;

import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.repository.ContactProxy;
import fr.boniric.paymybuddy.web.service.ContactService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestContactService {

    @Autowired
    ContactService contactService;

    @Autowired
    ContactProxy contactProxy;

    @Test
    public void verifyContactIsExistTest( ) {

        boolean boolContactIsPresent = false;
  //      Contact contact = new Contact(1, 1, "jon");

       System.out.println(contactProxy.getContactAll());
     //   contactProxy.saveContact(contact);

     //   boolContactIsPresent = contactService.verifyContactIsPresent(userId);

   //     Assertions.assertTrue(boolContactIsPresent);

    }
}
