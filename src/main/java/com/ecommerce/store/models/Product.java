package com.ecommerce.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean selected;

    @Column
    private boolean available;

    @Column
    private String photoName;
    @Transient
    private int quantity=1;
    @ManyToOne
    @JoinColumn(name = "catogory_id", updatable = false, insertable = false)
    private  Category category;
}
