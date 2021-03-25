package com.ecommerce.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date date;

    @OneToMany(mappedBy = "command")
    private Collection<CommandItem> commandItems;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @Column
    private double totalAmount;

    @OneToOne
    private Payment payment;


}
