package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.excepitons.BeerAlreadyRegisteredException;
import com.beerstock.excepitons.BeerNotFoundException;
import com.beerstock.services.repository.PersistenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    PersistenceRepository persistenceRepository;

    @Override
    public BeerResponse save(BeerRequest beerRequest) throws BeerAlreadyRegisteredException {
        verifyIfExists(beerRequest.getName());
        Beer beer = beerRequest.toModel();
        Beer savedBeer = persistenceRepository.save(beer);
        return savedBeer.toDTO();
    }

    @Override
    public BeerResponse findByName(String name){
        Beer foundBeer = persistenceRepository.findByName(name)
                .orElseThrow(() -> new BeerNotFoundException(name));
        return foundBeer.toDTO();
    }

    @Override
    public List<BeerResponse> list() {
        return persistenceRepository.findAll()
                .stream()
                .map(beer -> beer.toDTO())
                .collect(Collectors.toList());
    }

    private void verifyIfExists(String name) throws BeerAlreadyRegisteredException {
        Optional<Beer> beer = persistenceRepository.findByName(name);
        if(!beer.isEmpty()){
            throw new BeerAlreadyRegisteredException(beer.get().getName());
        }
    }
}
