package ro.zizicu.transaction.coordinator.data.entities;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;

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
    private DistributedTransactionStatus status;

    @Override
    public String getEntityName() {
        return "DistributedTransactions";
    }
}
