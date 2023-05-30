package com.example.productsample.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid;

    private String proName;

    private String proDesc;

    private long cid;

    @CreationTimestamp
    private String created;

    @UpdateTimestamp
    private String updated;

    @OneToMany(mappedBy = "product")
    private List<Option> options = new ArrayList<>();

}
