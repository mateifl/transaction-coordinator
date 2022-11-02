package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.zizicu.nwbase.data.NamedEntityRepository;
import ro.zizicu.transaction.coordinator.data.entities.Microservice;

import java.util.List;

@Repository
public interface MicroserviceRepository extends NamedEntityRepository<Microservice, Integer> {

//    List<Microservice> findByTransactionId(String transactionId);

}
