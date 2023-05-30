package com.example.productsample.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * 제품 정보
 */
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid; // 제품아이디

    private String proName; // 제품 이름

    private String proDesc; //제품 설명

    private long cid; // 제조사

    @CreationTimestamp
    private String created; // 등록일자

    @UpdateTimestamp
    private String updated; // 업데이트 일자

    @OneToMany
    @JoinColumn(name="pid")
    private List<Option> options = new ArrayList<>();

    public Product () {}

    @Builder
    public Product (long pid, String proName, String proDesc, long cid) {
        this.pid = pid;
        this.proName = proName;
        this.proDesc = proDesc;
        this.cid = cid;
    }
}
