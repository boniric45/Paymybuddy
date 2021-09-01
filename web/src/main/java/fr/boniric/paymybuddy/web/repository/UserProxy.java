package fr.boniric.paymybuddy.web.repository;

import fr.boniric.paymybuddy.web.custom.CustomProperties;
import fr.boniric.paymybuddy.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class UserProxy {

    @Autowired
    private CustomProperties props;

    public User getUserByEmail(String email){
        String baseApiUrl = props.getApiURL();
        String getUserURL =  baseApiUrl+"/user/"+email;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.GET,
                null,
                User.class
            );
        return response.getBody();
    }

    //ok
//    public Iterable<User> getUserAll() {
//        String baseApiUrl = props.getApiURL();
//        String getUserURL =  baseApiUrl+"/user/all";
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Iterable<User>> response = restTemplate.exchange(
//                getUserURL,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Iterable<User>>() {}
//        );
//        return response.getBody();
//    }

    public User createUser(User user) {
        String baseApiUrl = props.getApiURL();
        String getUserURL =  baseApiUrl+"/user";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<User>(user);
        ResponseEntity<User> response = restTemplate.exchange(
                getUserURL,
                HttpMethod.POST,
                request,
                User.class);

        log.debug("Create User call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
