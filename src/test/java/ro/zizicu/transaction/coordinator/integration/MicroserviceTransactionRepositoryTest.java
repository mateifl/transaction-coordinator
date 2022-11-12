package ro.zizicu.transaction.coordinator.integration;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.nwbase.transaction.TransactionStatus;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceTransactionRepository;

@SpringBootTest
public class MicroserviceTransactionRepositoryTest {

    @Autowired
    private MicroserviceTransactionRepository microserviceTransactionRepository;

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private DistributedTransactionRepository distributedTransactionRepository;

    @Test
    public void testSave() {
        MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();

        microserviceTransaction.setService(microserviceRepository.findByName("product"));
        microserviceTransaction.setTransaction(distributedTransactionRepository.findByTransactionId(1L).get());
        microserviceTransaction.setState(TransactionStatus.READY_TO_COMMIT);
        microserviceTransaction.setIsLast(Boolean.FALSE);
        assertNotNull(microserviceTransactionRepository.save(microserviceTransaction));
    }

}
