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
/**
* select시 join하기위한 제품 옵션
 * */
@Data
@Entity
@Table(name="product_option")
public class Option {
    @Id
    @GeneratedValue
    private long oid; //option 고유아이디

    private String size; // 제품 사이즈

    private int qty; // 재고

    private int price; // 제품 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pid")
    @JsonBackReference
    public Product product;
}
