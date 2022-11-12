package ro.zizicu.transaction.coordinator.data.repository;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.transaction.coordinator.data.entities.Microservice;

public interface MicroserviceRepository extends CrudRepository<Microservice, Integer> {

    Microservice findByName(String name);

}
