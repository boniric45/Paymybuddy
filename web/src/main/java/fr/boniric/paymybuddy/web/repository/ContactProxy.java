package fr.boniric.paymybuddy.web.repository;

import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ContactProxy {

    @Autowired
    private CustomProperties props;

    public Contact saveContact(int userAuthId, int newContactId){
        String baseApiUrl = props.getApiURL();
        String getContactURL =  baseApiUrl+"/contact/"+userAuthId+"/"+newContactId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contact> response = restTemplate.exchange(
                getContactURL,
                HttpMethod.POST,
                null,
                Contact.class );
        return response.getBody();
    }

}
