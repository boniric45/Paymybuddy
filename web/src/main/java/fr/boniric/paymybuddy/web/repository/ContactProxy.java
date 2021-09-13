package fr.boniric.paymybuddy.web.repository;

import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ContactProxy {

    @Autowired
    private CustomProperties props;

    public void saveContact(Contact contact) {
        String baseApiUrl = props.getApiURL();
        String getContactURL = baseApiUrl + "/contact";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Contact> request = new HttpEntity<Contact>(contact);
        ResponseEntity<Contact> response = restTemplate.exchange(
                getContactURL,
                HttpMethod.POST,
                request,
                Contact.class);
        response.getBody();
    }

    public String listContact(int userAuthId) {
        String baseApiUrl = props.getApiURL();
        String getContactURL = baseApiUrl + "/contact/" + userAuthId;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(String.valueOf(userAuthId));
        ResponseEntity<String> response = restTemplate.exchange(
                getContactURL,
                HttpMethod.GET,
                request,
                String.class);

        return response.getBody();
    }



}
