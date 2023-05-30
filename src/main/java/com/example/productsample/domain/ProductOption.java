package com.example.productsample.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name="product_option")
public class ProductOption {
    @Id
    @GeneratedValue
    private long oid;

    private String size;

    private int qty;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pid")
    @JsonBackReference
    public Product product;
}
