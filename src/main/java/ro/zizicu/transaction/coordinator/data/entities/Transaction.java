package ro.zizicu.transaction.coordinator.data.entities;

import lombok.Data;
import ro.zizicu.nwbase.entity.IdentityOwner;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "transactions")
public class Transaction implements IdentityOwner<Integer> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Override
    public String getEntityName() {
        return "Transaction";
    }
}
