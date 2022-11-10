package ro.zizicu.transaction.coordinator.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.zizicu.nwbase.transaction.TransactionStatus;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DistributedTransactionRepositoryTest {

    @Autowired
    private DistributedTransactionRepository transactionRepository;


    @Test
    public void testSave() {

        DistributedTransaction distributedTransaction = new DistributedTransaction();
        distributedTransaction.setTransactionId(1L);
        distributedTransaction.setStatus(TransactionStatus.UNCOMMITED);
//        distributedTransaction.setTransactionDate(new Date());
        assertNotNull(transactionRepository.save(distributedTransaction));

        DistributedTransaction distributedTransaction2 = new DistributedTransaction();
        distributedTransaction2.setTransactionId(2L);
        distributedTransaction2.setStatus(TransactionStatus.COMMITED);
//        distributedTransaction.setTransactionDate(new Date());
        assertNotNull(transactionRepository.save(distributedTransaction2));

    }


}
