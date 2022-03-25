package com.beerstock.entities;

import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.enums.BeerTypeEnum;
import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    @Enumerated(value = EnumType.STRING)
    private BeerTypeEnum type;

    public BeerResponse toDTO(){
        return BeerResponse.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .max(max)
                .quantity(quantity)
                .type(type)
                .build();
    }
}
