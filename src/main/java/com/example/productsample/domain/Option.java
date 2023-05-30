package com.example.productsample.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="product_option")
public class Option {
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
