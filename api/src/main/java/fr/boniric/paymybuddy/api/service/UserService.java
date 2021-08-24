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

    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUser() { return userRepository.findAll(); }

    public User saveUser (User user) { return userRepository.save(user); }

    public void deleteUser( Integer id) { userRepository.deleteById(id);}

    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email);}


}

