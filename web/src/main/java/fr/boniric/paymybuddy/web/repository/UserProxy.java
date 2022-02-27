package fr.boniric.paymybuddy.web.repository;

import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class UserProxy {

    @Autowired
    private CustomProperties props;

    public User getUserByEmail(String email) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/user/" + email;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                User.class
        );
        return response.getBody();
    }

    public User getUserById(int userId) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/users/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                User.class);

        log.debug("Get User By Id call " + response.getStatusCode());
        return response.getBody();
    }

    public User createUser(User user) {
        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/user";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.POST,
                request,
                User.class);

        log.debug("Create User call " + response.getStatusCode());
        return response.getBody();
    }

    public void updateUser(Integer userId, double amountTransaction) {

        String baseApiUrl = props.getApiURL();
        String getUserURL = baseApiUrl + "/updateBalance/" + userId + "/" + amountTransaction;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.PUT,
                null,
                User.class);

        log.debug("Create User call " + response.getStatusCode());
    }

}
