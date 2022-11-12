package ro.zizicu.transaction.coordinator.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.transaction.TransactionMessage;
import ro.zizicu.nwbase.transaction.TransactionStatus;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;
import ro.zizicu.transaction.coordinator.data.entities.Microservice;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceTransactionRepository;
import ro.zizicu.transaction.coordinator.services.CoordinationService;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultCoordinationService implements CoordinationService {

    private final MicroserviceTransactionRepository microserviceTransactionRepository;
    private final MicroserviceRepository microserviceRepository;
    private final DistributedTransactionRepository distributedTransactionRepository;

    @Override
    public MicroserviceTransaction createTransactionStep(TransactionMessage transactionMessage) {
        log.debug("creating transaction step {}", transactionMessage.toString());
        Microservice microservice = microserviceRepository.findByName(transactionMessage.getServiceName());
        MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();
        microserviceTransaction.setService(microservice);

        Optional<DistributedTransaction> distributedTransactionOptional =
                distributedTransactionRepository.findByTransactionId(transactionMessage.getTransactionId());
        if(distributedTransactionOptional.isEmpty()) {
            DistributedTransaction distributedTransaction = new DistributedTransaction();
            distributedTransaction.setTransactionId(transactionMessage.getTransactionId());
            distributedTransactionOptional = Optional.of(distributedTransaction);
        }
        microserviceTransaction.setTransaction(distributedTransactionOptional.get());
        microserviceTransaction.setIsLast(transactionMessage.getIsLastStep());
        if(transactionMessage.getIsSuccessful())
        	microserviceTransaction.setState(TransactionStatus.READY_TO_COMMIT);
        else
        	microserviceTransaction.setState(TransactionStatus.ROLLEDBACK);
        log.debug("persist microservice transaction {}", microserviceTransaction.toString());
        return microserviceTransactionRepository.save(microserviceTransaction);
    }

    @Override
    public TransactionStatusMessage getTransactionStatus(Long transactionId) {
        Optional<DistributedTransaction> distributedTransactionOptional =
                distributedTransactionRepository.findByTransactionId(transactionId);
        return null;
    }

    @Override
    public void updateTransactionStatus() {

    }

}
