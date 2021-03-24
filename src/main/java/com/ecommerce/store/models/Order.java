package com.ecommerce.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date date;
    @OneToMany(mappedBy = "order")
    @JoinColumn(name = "order_id")
    private Collection<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    private Client client;
    @Column
    private double totalAmount;
    @OneToOne
    private Payment payment;


}
