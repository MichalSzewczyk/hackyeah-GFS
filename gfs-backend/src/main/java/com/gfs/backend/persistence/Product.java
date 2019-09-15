package com.gfs.backend.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class Product {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column @Getter @Setter
    private String barCode;

    @Column @Getter @Setter
    private String country;

    @Column @Getter @Setter
    private String productName;

    @Column @Getter @Setter
    private String manufacturer;

    @Column @Getter @Setter
    private String dangerousIngredients;

    @Column @Getter @Setter
    private Long greenFootrpint;
}
