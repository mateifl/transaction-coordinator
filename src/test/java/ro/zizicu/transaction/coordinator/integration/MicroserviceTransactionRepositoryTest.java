package ro.zizicu.transaction.coordinator.integration;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceTransactionRepository;
import ro.zizicu.transaction.coordinator.utils.DateUtils;

import java.util.Optional;

@SpringBootTest
public class MicroserviceTransactionRepositoryTest {

    @Autowired
    private MicroserviceTransactionRepository microserviceTransactionRepository;

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private DistributedTransactionRepository distributedTransactionRepository;

    @BeforeEach
    public void setUp() {
        microserviceTransactionRepository.deleteAll();
        distributedTransactionRepository.deleteAll();
    }

    @Test
    public void testSave() {

        DistributedTransaction distributedTransaction = new DistributedTransaction();
        distributedTransaction.setTransactionId(10000L);
        distributedTransaction.setStatus(DistributedTransactionStatus.READY_TO_COMMIT);
        distributedTransaction.setTransactionDate(DateUtils.getSQLDateNow());
        distributedTransaction = distributedTransactionRepository.save(distributedTransaction);

        MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();
        microserviceTransaction.setService(microserviceRepository.findByName("product"));
        microserviceTransaction.setTransaction(distributedTransactionRepository.findAll().iterator().next());
        microserviceTransaction.setState(DistributedTransactionStatus.READY_TO_COMMIT);
        microserviceTransaction.setIsLast(Boolean.FALSE);
        assertNotNull(microserviceTransactionRepository.save(microserviceTransaction));
    }

    
    @Test
    public void testCheckTransaction() {

        DistributedTransaction distributedTransaction = null;

        Optional<DistributedTransaction> dt = distributedTransactionRepository.findByTransactionId(10000L);

        if(dt.isEmpty()) {
            distributedTransaction = new DistributedTransaction();
            distributedTransaction.setTransactionId(10000L);
            distributedTransaction.setStatus(DistributedTransactionStatus.READY_TO_COMMIT);
            distributedTransaction.setTransactionDate(DateUtils.getSQLDateNow());
            distributedTransaction = distributedTransactionRepository.save(distributedTransaction);
        }
        else {
            distributedTransaction = dt.get();
        }

    	MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();
        microserviceTransaction.setService(microserviceRepository.findByName("product"));
        microserviceTransaction.setTransaction(distributedTransaction);
        microserviceTransaction.setState(DistributedTransactionStatus.READY_TO_COMMIT);
        microserviceTransaction.setIsLast(Boolean.FALSE);
    	
    	MicroserviceTransaction microserviceTransaction1 = new MicroserviceTransaction();
        microserviceTransaction1.setService(microserviceRepository.findByName("order"));
        microserviceTransaction1.setTransaction(distributedTransaction);
        microserviceTransaction1.setState(DistributedTransactionStatus.READY_TO_COMMIT);
        microserviceTransaction1.setIsLast(Boolean.TRUE);
        
    	microserviceTransactionRepository.save(microserviceTransaction);
    	microserviceTransactionRepository.save(microserviceTransaction1);
    	
    	assertEquals(2, microserviceTransactionRepository.findAllByTransactionTransactionId(10000L).size());

        microserviceTransactionRepository.delete(microserviceTransaction);
        microserviceTransactionRepository.delete(microserviceTransaction1);
        distributedTransactionRepository.delete(distributedTransaction);
    }
    
}
