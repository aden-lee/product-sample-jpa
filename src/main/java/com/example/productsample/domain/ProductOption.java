package com.example.productsample.domain;


import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name="product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oid;

    private String size;

    private long pid;

    private int qty;

    private int price;

}
