package ro.zizicu.transaction.coordinator.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ro.zizicu.nwbase.transaction.TransactionMessage;
import ro.zizicu.transaction.coordinator.data.entities.Microservice;
import ro.zizicu.transaction.coordinator.data.entities.ServiceTransaction;
import ro.zizicu.transaction.coordinator.data.entities.Transaction;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceRepository;
import ro.zizicu.transaction.coordinator.data.repository.ServiceTransactionRepository;
import ro.zizicu.transaction.coordinator.data.repository.TransactionRepository;
import ro.zizicu.transaction.coordinator.services.CoordinationService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultCoordinationService implements CoordinationService {

    private final ServiceTransactionRepository serviceTransactionRepository;
    private final MicroserviceRepository microserviceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @KafkaListener(topics = "stockUpdateTopic", groupId = "test")
    public void processMessage(TransactionMessage message) {
        log.info("received transaction message {}", message.toString());

        Microservice microservice = microserviceRepository.findByName( message.getServiceName() ).get(0);

        ServiceTransaction serviceTransaction = new ServiceTransaction();
        serviceTransaction.setService(microservice);
        Transaction transaction = transactionRepository.findByTransactionId(message.getTransactionId());
        serviceTransaction.setTransaction(transaction);
        log.debug("Save service transaction {}", message.getTransactionId());
        serviceTransaction.setIsLast(message.getIsLastStep());
        serviceTransactionRepository.save(serviceTransaction);

        if( message.getIsLastStep() ) {
            log.debug("Find all steps for transaction {}", message.getTransactionId());
            List<ServiceTransaction> serviceTransactions = serviceTransactionRepository.findAllByTransactionId(message.getTransactionId());
            log.debug("Sending commit message for transaction {}", message.getTransactionId());

        }
    }
}
