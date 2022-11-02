package ro.zizicu.transaction.coordinator.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.zizicu.transaction.coordinator.data.entities.Transaction;
import ro.zizicu.transaction.coordinator.data.repository.TransactionRepository;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    public void testSave() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1112221112);
        Transaction saved = transactionRepository.save(transaction);
        assertNotNull(saved);
    }


}
