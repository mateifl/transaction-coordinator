package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;

import java.util.Optional;


public interface DistributedTransactionRepository extends CrudRepository<DistributedTransaction, Integer> {
    Optional<DistributedTransaction> findByTransactionId(Long transactionId);
}
