package ro.zizicu.transaction.coordinator.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.transaction.coordinator.data.entities.DistributedTransaction;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;
import ro.zizicu.transaction.coordinator.data.repository.DistributedTransactionRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceRepository;
import ro.zizicu.transaction.coordinator.data.repository.MicroserviceTransactionRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class DistributedTransactionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DistributedTransactionRepository distributedTransactionRepository;


    @Autowired
    private MicroserviceTransactionRepository microserviceTransactionRepository;

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Test
    public void testDistributedTransaction() {
        Long transactionId = jdbcTemplate.query("select nextval(sq_transaction)", new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getLong(1);
            }
        });
        log.debug("transaction id = {}", transactionId);

        Calendar now = Calendar.getInstance();
        DistributedTransaction distributedTransaction = new DistributedTransaction();
        distributedTransaction.setTransactionId(transactionId);
        distributedTransaction.setStatus(DistributedTransactionStatus.UNCOMMITED);
        distributedTransaction.setTransactionDate(new Date(now.getTimeInMillis()));
        DistributedTransaction fromDB = distributedTransactionRepository.save(distributedTransaction);
        assertNotNull(fromDB);

        MicroserviceTransaction microserviceTransaction = new MicroserviceTransaction();

        microserviceTransaction.setService(microserviceRepository.findByName("product"));
        microserviceTransaction.setTransaction(fromDB);
        microserviceTransaction.setState(DistributedTransactionStatus.READY_TO_COMMIT);
        microserviceTransaction.setIsLast(Boolean.FALSE);
        assertNotNull(microserviceTransactionRepository.save(microserviceTransaction));
    }

}
