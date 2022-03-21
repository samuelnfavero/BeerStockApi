package com.beerstock.dtos.response;

import com.beerstock.enums.BeerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class BeerResponse {
    private Long id;
    private String name;
    private String brand;
    private int max;
    private int quantity;
    private BeerTypeEnum type;
}
