package com.beerstock.builders;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.enums.BeerTypeEnum;
import lombok.Builder;

@Builder
public class FakeBeer {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Brahma";
    @Builder.Default
    private String brand = "Ambev";
    @Builder.Default
    private int max = 30;
    @Builder.Default
    private int quantity = 10;
    @Builder.Default
    private BeerTypeEnum type = BeerTypeEnum.PILSEN;

    public Beer toModel(){
        return Beer.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .max(30)
                .quantity(10)
                .type(type)
                .build();
    }

    public BeerRequest toRequest(){
        return BeerRequest.builder()
                .name(name)
                .brand(brand)
                .max(30)
                .quantity(10)
                .type(type)
                .build();
    }

    public BeerResponse toResponse(){
        return BeerResponse.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .max(30)
                .quantity(10)
                .type(type)
                .build();
    }

}
