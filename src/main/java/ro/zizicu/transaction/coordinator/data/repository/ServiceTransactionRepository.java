package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.zizicu.transaction.coordinator.data.entities.ServiceTransaction;

import java.util.List;

@Repository
public interface ServiceTransactionRepository extends CrudRepository<ServiceTransaction, Integer> {


    List<ServiceTransaction> findAllByTransactionId(Long transactionId);

}
