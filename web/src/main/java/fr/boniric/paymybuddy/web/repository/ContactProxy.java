package fr.boniric.paymybuddy.web.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.Contact;
import fr.boniric.paymybuddy.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ContactProxy {

    @Autowired
    CustomProperties props;

    public void saveContact(Contact contact) {
        String baseApiUrl = props.getApiURL();
        String getContactURL = baseApiUrl + "/contact";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Contact> request = new HttpEntity<>(contact);
        ResponseEntity<Contact> response = restTemplate.exchange(
                getContactURL,
                HttpMethod.POST,
                request,
                Contact.class);
        log.debug("Create Contact call " + response.getStatusCode().toString());
         response.getBody();
    }

    public String listContact(int userAuthId) {
        String baseApiUrl = props.getApiURL();
        String getContactURL = baseApiUrl + "/listcontact/" + userAuthId;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(String.valueOf(userAuthId));
        ResponseEntity<String> response = restTemplate.exchange(
                getContactURL,
                HttpMethod.GET,
                request,
                String.class);
        return response.getBody();
    }

    public Contact getContactAll(){
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/contact/all";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contact> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                Contact.class);

        log.debug("Get Contact All call " + response.getStatusCode());
        return response.getBody();

    }

    public Contact getContactById(int userId) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/contact/"+userId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contact> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                Contact.class);

        log.debug("Get Contact By Id call " + response.getStatusCode());
        return response.getBody();
    }
}
