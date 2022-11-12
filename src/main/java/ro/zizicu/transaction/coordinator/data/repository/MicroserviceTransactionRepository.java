package ro.zizicu.transaction.coordinator.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;

public interface MicroserviceTransactionRepository extends CrudRepository<MicroserviceTransaction, Integer> {
    List<MicroserviceTransaction> findAllByTransactionId(Long transactionId);
}
