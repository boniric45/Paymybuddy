package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Iterable<User> getUserById(long id);

}
