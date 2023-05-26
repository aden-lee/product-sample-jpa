package com.example.productsample.domain;

import lombok.*;

import java.io.Serializable;


@Data
public class CodesPK implements Serializable {
    private String groupCode;
    private String subCode;
}
