package com.beerstock.dtos.request;

import com.beerstock.entities.Beer;
import com.beerstock.enums.BeerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BeerRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @Size(min = 1, max = 100)
    private String brand;
    @NotNull
    @Max(500)
    private int max;
    @NotNull
    @Max(100)
    private int quantity;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BeerTypeEnum type;

    public Beer toModel(){
        return Beer.builder()
                .name(name)
                .brand(brand)
                .max(max)
                .quantity(quantity)
                .type(type)
                .build();
    }
}
