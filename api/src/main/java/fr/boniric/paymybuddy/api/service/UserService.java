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

    /**
     * Get User with email user
     *
     * @param email
     * @return Optional<User>
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get User with  user id
     *
     * @param authId
     * @return Iterable<User>
     */
    public Iterable<User> getUserById(long authId) {
        return userRepository.getUserById(authId);
    }

    /**
     * Get All User with CRUD Method
     *
     * @return Iterable<User>
     */
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Delete User with CRUD Method
     * <p>
     * Use for Test
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Save User with CRUD Method
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Delete User by user id with CRUD Method
     *
     * Use for Test
     */
    public void deleteUserById(long idUser) {
        userRepository.deleteById(idUser);
    }
}

