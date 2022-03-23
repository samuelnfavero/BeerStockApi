package com.beerstock.excepitons;

public class BeerNotFoundException extends RuntimeException {

    public BeerNotFoundException(String name){
        super(String.format("Cerveja de nome %s não encontrada", name));
    }
}
