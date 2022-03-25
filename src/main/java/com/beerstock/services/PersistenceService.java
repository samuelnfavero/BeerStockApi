package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.excepitons.BeerAlreadyRegisteredException;
import com.beerstock.excepitons.StockMaxCapacityException;

import java.util.List;

public interface PersistenceService {
    BeerResponse save(BeerRequest beerRequest) throws BeerAlreadyRegisteredException;

    List<BeerResponse> list();

    BeerResponse findByName(String name);

    void updateBeerStock(String name, int quantity) throws BeerAlreadyRegisteredException, StockMaxCapacityException;
}
