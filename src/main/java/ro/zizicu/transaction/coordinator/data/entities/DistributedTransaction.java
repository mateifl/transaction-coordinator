package ro.zizicu.transaction.coordinator.data.entities;


import lombok.Data;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.transaction.TransactionStatus;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "distributed_transactions")
@Data
public class DistributedTransaction implements IdentityOwner<Integer> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column
    private TransactionStatus status;

    @Override
    public String getEntityName() {
        return "DistributedTransactions";
    }
}
