package ro.zizicu.transaction.coordinator.data.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import ro.zizicu.nwbase.entity.NamedIdentityOwner;

@Entity
@Data
@Table(name = "microservices")
public class Microservice implements NamedIdentityOwner<Integer> {

    @Id
    @Column(name = "service_id")
    private Integer id;
    @Column(name = "service_name")
    private String name;

    @Override
    public String getEntityName() {
        return "Microservice";
    }
}
