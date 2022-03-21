package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.services.repository.PersistenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    PersistenceRepository persistenceRepository;

    @Override
    public BeerResponse save(BeerRequest beerRequest) {
        Beer beer = beerRequest.toModel();
        Beer savedBeer = persistenceRepository.save(beer);
        return savedBeer.toDTO();
    }
}
