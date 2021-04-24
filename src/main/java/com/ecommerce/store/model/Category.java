package com.ecommerce.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor@EqualsAndHashCode
@Table(name = "category")
public class Category extends Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private byte[] photo;

    @Column
    private String description;

    @OneToMany(mappedBy = "category")
    private Collection<Product> products;
}
