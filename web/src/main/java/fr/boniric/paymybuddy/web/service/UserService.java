package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.UserProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserProxy userProxy;

    public User getUserByEmail(String email) {
        return userProxy.getUserByEmail(email);
    }

    public Iterable<User> getUserAll() { return userProxy.getUserAll();}


}
