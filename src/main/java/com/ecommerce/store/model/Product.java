package com.ecommerce.store.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
@EqualsAndHashCode
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double currentPrice;

    @Column
    private boolean promotion;

    @Column
    private boolean available;

    @Column
    private byte[] photo;

    @Column
    private int quantity ;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
