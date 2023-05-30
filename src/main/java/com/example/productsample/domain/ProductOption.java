package com.example.productsample.domain;


import jakarta.persistence.*;
import lombok.*;

/**
 * 제품 옵션
 * 제품 입력시 사용
 */
@Data
@Entity
@Table(name="product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oid; // 옵션아이디

    private String size; // 제품사이즈

    private long pid; // 제품아이디

    private int qty; // 재고

    private int price; // 제품가격

    public ProductOption () {}

    @Builder
    public ProductOption (long pid, long oid, String size, int qty, int price) {
        this.pid = pid;
        this.oid = oid;
        this.size = size;
        this.qty = qty;
        this.price = price;
    }
}
