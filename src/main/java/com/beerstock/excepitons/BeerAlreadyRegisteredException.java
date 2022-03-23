package com.beerstock.excepitons;

public class BeerAlreadyRegisteredException extends Exception {
    public BeerAlreadyRegisteredException(String name) {
        super(String.format("Cerveja de nome %s já cadastrada", name));
    }
}
