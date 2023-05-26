package com.example.productsample.dto;

import com.example.productsample.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private long pid;

    private String proName;

    private String proDesc;

    private long cid;

    private String created;

    private String updated;

    public ProductResponse(Product product) {
        this.pid = product.getPid();
        this.proName = product.getProName();
        this.proDesc = product.getProDesc();
        this.cid = product.getCid();
        this.created = product.getCreated();
        this.updated = product.getUpdated();
    }
}
