package ro.zizicu.transaction.coordinator.integration;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;
import ro.zizicu.transaction.coordinator.utils.DateUtils;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

@SpringBootTest
public class DistributedTransactionRepositoryTest {

    @Autowired
    private DistributedTransactionRepository distributedTransactionRepository;

    @Test
    public void testSave() {

        Optional<DistributedTransaction> dt1 = distributedTransactionRepository.findByTransactionId(12121212L);
        dt1.ifPresent(distributedTransaction -> distributedTransactionRepository.delete(distributedTransaction));

        Optional<DistributedTransaction> dt2 = distributedTransactionRepository.findByTransactionId(12121213L);
        dt2.ifPresent(distributedTransaction -> distributedTransactionRepository.delete(distributedTransaction));

        Calendar now = Calendar.getInstance();
        DistributedTransaction distributedTransaction = new DistributedTransaction();
        distributedTransaction.setTransactionId(12121212L);
        distributedTransaction.setStatus(DistributedTransactionStatus.UNCOMMITED);
        distributedTransaction.setTransactionDate(DateUtils.getSQLDateNow());
        assertNotNull(distributedTransactionRepository.save(distributedTransaction));

        DistributedTransaction distributedTransaction2 = new DistributedTransaction();
        distributedTransaction2.setTransactionId(12121213L);
        distributedTransaction2.setStatus(DistributedTransactionStatus.COMMITED);
        distributedTransaction2.setTransactionDate(new Date(now.getTimeInMillis()));
        assertNotNull(distributedTransactionRepository.save(distributedTransaction2));

    }


}
