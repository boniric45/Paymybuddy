package fr.boniric.paymybuddy.api.service;

import fr.boniric.paymybuddy.api.model.User;
import fr.boniric.paymybuddy.api.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> getUserById(long authId) {
        return userRepository.getUserById(authId);
    }

    public Iterable<User> getAllUser() { return userRepository.findAll(); }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(long idUser) {
        userRepository.deleteById(idUser);
        }
}

