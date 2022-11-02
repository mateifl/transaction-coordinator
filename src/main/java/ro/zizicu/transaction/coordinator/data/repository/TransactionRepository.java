package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.zizicu.transaction.coordinator.data.entities.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Transaction findByTransactionId(Long transactionId);
}
