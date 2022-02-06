package fr.boniric.paymybuddy.web.repository;

import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public List<Contact> getContactAll() {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/contact/all";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Contact>> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Contact>>() {
                });

        log.debug("Get Contact All call " + response.getStatusCode());
        return response.getBody();

    }

    public Contact getControlContactById(int userId, int buddyId) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/contact/" + userId + "/" + buddyId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contact> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Contact>() {
                });

        log.debug("Get Contact By Id call " + response.getStatusCode());
        return response.getBody();
    }

    public Contact getContactById(int userId) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/contact/" + userId;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Contact> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Contact>() {
                });

        log.debug("Get Contact By Id call " + response.getStatusCode());
        return response.getBody();
    }

}
