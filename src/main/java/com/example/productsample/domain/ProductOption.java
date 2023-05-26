package com.example.productsample.domain;

import jakarta.persistence.*;
import lombok.*;


@Data
@ToString(exclude = "product")
@Entity
@Table(name="product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oid;

    private long pid;

    private String size;

    private int qty;

    private int price;

    @ManyToOne(optional=false)
    @JoinColumn(name="pid", referencedColumnName="pid", insertable=false, updatable=false)
    public Product product;
}
