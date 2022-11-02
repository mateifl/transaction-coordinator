package ro.zizicu.transaction.coordinator.data.entities;


import lombok.Data;
import ro.zizicu.nwbase.entity.IdentityOwner;

import javax.persistence.*;

@Entity
@Data
@Table(name = "service_transactions")
public class ServiceTransaction implements IdentityOwner<Integer> {

    @Id
    @Column(name = "service_transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String state;

    @Column(name = "is_last_step")
    private Boolean isLast;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Microservice service;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;


    @Override
    public String getEntityName() {
        return "ServiceTransaction";
    }
}
