package com.beerstock.excepitons;

public class BeerNameNotFoundException extends RuntimeException {

    public BeerNameNotFoundException(String name){
        super("Nome já cadatrado: " + name);
    }
}
