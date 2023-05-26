package com.example.productsample.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CodesPK.class)
public class Codes {
    @Id
    @Column(name = "group_code")
    private String groupCode;

    @Id
    @Column(name = "sub_code")
    private String subCode;

    @Column(name = "name")
    private String name;
}
