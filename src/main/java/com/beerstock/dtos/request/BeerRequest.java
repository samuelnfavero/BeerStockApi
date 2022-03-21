package com.beerstock.dtos.request;

import com.beerstock.enums.BeerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BeerRequest {
    private String name;
    private String brand;
    private int max;
    private int quantity;
    private BeerTypeEnum type;
}
