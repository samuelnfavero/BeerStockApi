package com.beerstock.entities;

import com.beerstock.enums.BeerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@Getter
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private int max;
    @Column
    private int quantity;
    @Column
    private BeerTypeEnum type;
}
