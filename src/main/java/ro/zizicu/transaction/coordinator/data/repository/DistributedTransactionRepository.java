package ro.zizicu.transaction.coordinator.data.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;


public interface DistributedTransactionRepository extends CrudRepository<DistributedTransaction, Integer> {
    Optional<DistributedTransaction> findByTransactionId(Long transactionId);
}
