package com.beerstock.services;

import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.excepitons.BeerNameNotFoundException;
import com.beerstock.services.repository.PersistenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    PersistenceRepository persistenceRepository;

    @Override
    public BeerResponse save(BeerRequest beerRequest) {
        verifyIfExists(beerRequest.getName());
        Beer beer = beerRequest.toModel();
        Beer savedBeer = persistenceRepository.save(beer);
        return savedBeer.toDTO();
    }

    @Override
    public List<BeerResponse> list() {
        return persistenceRepository.findAll()
                .stream()
                .map(beer -> beer.toDTO())
                .collect(Collectors.toList());
    }

    private void verifyIfExists(String name){
        Optional<Beer> beer = findByName(name);
        if(!beer.isEmpty()){
            throw new BeerNameNotFoundException(beer.get().getName());
        }
    }

    private Optional<Beer> findByName(String name){
        return persistenceRepository.findByName(name);

    }
}
