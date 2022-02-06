package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.PaymentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentType, Integer> {

}
