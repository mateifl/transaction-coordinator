package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;


import java.util.List;

public interface MicroserviceTransactionRepository extends CrudRepository<MicroserviceTransaction, Integer> {
    List<MicroserviceTransaction> findAllByTransactionId(Long transactionId);
}
