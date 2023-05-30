package com.example.productsample.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private long pid;

    private String proName;

    private String proDesc;

    private long cid;

    @CreationTimestamp
    private String created;

    @UpdateTimestamp
    private String updated;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptions = new ArrayList<>();

}
