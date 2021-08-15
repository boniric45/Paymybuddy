package fr.boniric.paymybuddy.api.repository;

import fr.boniric.paymybuddy.api.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

}
