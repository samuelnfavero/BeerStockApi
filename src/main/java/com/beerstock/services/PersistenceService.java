package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;

public interface PersistenceService {
    BeerResponse save(BeerRequest beerRequest);
}
