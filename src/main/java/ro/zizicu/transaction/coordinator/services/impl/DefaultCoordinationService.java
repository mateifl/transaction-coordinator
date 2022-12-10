package ro.zizicu.transaction.coordinator.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

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
import ro.zizicu.transaction.coordinator.exceptions.DistributedTransactionNotFound;
import ro.zizicu.transaction.coordinator.services.CoordinationService;
import ro.zizicu.transaction.coordinator.utils.DateUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultCoordinationService implements CoordinationService {

    private final MicroserviceTransactionRepository microserviceTransactionRepository;
    private final MicroserviceRepository microserviceRepository;
    private final DistributedTransactionRepository distributedTransactionRepository;

    @Override
    @Transactional
    public MicroserviceTransaction createTransactionStep(TransactionMessage transactionMessage) {
        log.debug("creating transaction step {}", transactionMessage.toString());
        Microservice microservice = microserviceRepository.findByName(transactionMessage.getServiceName());
        MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();
        microserviceTransaction.setService(microservice);

        Optional<DistributedTransaction> distributedTransactionOptional =
                distributedTransactionRepository.findByTransactionId(transactionMessage.getTransactionId());
        if(distributedTransactionOptional.isEmpty()) {
        	log.info("distributed transaction with id {} not found, creating a new one", transactionMessage.getTransactionId());
            DistributedTransaction distributedTransaction = new DistributedTransaction();
            distributedTransaction.setStatus(TransactionStatus.UNCOMMITED);
            distributedTransaction.setTransactionDate(DateUtils.getSQLDateNow());
            distributedTransaction.setTransactionId(transactionMessage.getTransactionId());
            distributedTransactionOptional = Optional.of(distributedTransaction);
        }
        microserviceTransaction.setTransaction(distributedTransactionOptional.get());
        microserviceTransaction.setIsLast(transactionMessage.getIsLastStep());
        if(transactionMessage.getIsSuccessful())
        	microserviceTransaction.setState(TransactionStatus.READY_TO_COMMIT);
        else
        	microserviceTransaction.setState(TransactionStatus.ROLLEDBACK);
        log.debug("persist microservice transaction {}", microserviceTransaction);
        microserviceTransaction = microserviceTransactionRepository.save(microserviceTransaction);
        if(microserviceTransaction.getIsLast())
        {
        	log.info("last step, checking distributed transaction status {}", transactionMessage.getTransactionId());
        	boolean readyToCommit = microserviceTransactionRepository.findAllByTransactionId( transactionMessage.getTransactionId() )
        		.stream()
        		.allMatch( v -> v.getState() == TransactionStatus.READY_TO_COMMIT );
        	
        	if(readyToCommit) {
        		log.info("distributed transaction {} ready to commit", transactionMessage.getTransactionId());
        		distributedTransactionOptional.get().setStatus(TransactionStatus.READY_TO_COMMIT);
        		distributedTransactionRepository.save(distributedTransactionOptional.get());
        	}
        }
        
        return microserviceTransaction;
    }

    @Override
    public TransactionStatusMessage getTransactionStatus(Long transactionId) {
        log.info("get status for distributed transaction {}", transactionId);
        Optional<DistributedTransaction> distributedTransactionOptional =
                distributedTransactionRepository.findByTransactionId(transactionId);
        if(distributedTransactionOptional.isPresent()) {
            TransactionStatusMessage message = new TransactionStatusMessage();
            message.setStatus(distributedTransactionOptional.get().getStatus());
            message.setTransactionId(transactionId);
            return message;
        }
        else
            throw new DistributedTransactionNotFound();

    }

    @Override
    public void updateTransactionStatus() {

    }

}
