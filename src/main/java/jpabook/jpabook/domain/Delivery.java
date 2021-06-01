package jpabook.jpabook.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "Delivery_ID")
    private Long id;

    @OneToOne(mappedBy = "Delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus stauts;
}
