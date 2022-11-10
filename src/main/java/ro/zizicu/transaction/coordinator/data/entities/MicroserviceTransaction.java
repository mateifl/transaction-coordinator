package ro.zizicu.transaction.coordinator.data.entities;


import lombok.Data;
import lombok.ToString;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.transaction.TransactionStatus;


import javax.persistence.*;

@Data
@Entity
@Table(name = "microservice_transactions")
@ToString
public class MicroserviceTransaction implements IdentityOwner<Integer> {

    @Id
    @Column(name = "service_transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private TransactionStatus state;

    @Column(name = "is_last_step")

    private Boolean isLast;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Microservice service;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private DistributedTransaction transaction;

    @Override
    public String getEntityName() {
        return "MicroserviceTransaction";
    }
}
