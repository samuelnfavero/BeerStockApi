package com.beerstock.excepitons;

public class BeerNotFoundException extends RuntimeException {

    public BeerNotFoundException(String name){
        super(String.format("Cerveja de nome %s não encontrada", name));
    }
    
    public BeerNotFoundException(Long id){
        super(String.format("Cerveja com ID %s não encontrada", id));
    }
}
