package fr.boniric.paymybuddy.web.service;

import fr.boniric.paymybuddy.web.model.User;
import fr.boniric.paymybuddy.web.repository.ContactProxy;
import fr.boniric.paymybuddy.web.repository.UserProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Data
@Service
public class UserService {

    @Autowired
    private UserProxy userProxy;

    public User getUserByEmail(String email) {
        return userProxy.getUserByEmail(email);
    }

    public void saveUser(User user) {
        userProxy.createUser(user);
    }

    public Iterable<User> getUserById(int userId) {
        return Collections.singleton(userProxy.getUserById(userId));
    }

    public void updateUser(Integer userId, double amountTransaction) {
        userProxy.updateUser(userId, amountTransaction);
    }

}
