package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;

import java.util.List;

public interface PersistenceService {
    BeerResponse save(BeerRequest beerRequest);

    List<BeerResponse> list();

}
