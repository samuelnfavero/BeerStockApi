package com.beerstock.controllers;


import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.excepitons.BeerAlreadyRegisteredException;
import com.beerstock.services.PersistenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beerstock")
@AllArgsConstructor
public class BeerController {

    PersistenceService persistenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerResponse save(@RequestBody @Valid BeerRequest beerRequest) throws BeerAlreadyRegisteredException {
        return persistenceService.save(beerRequest);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public BeerResponse findByName(@PathVariable String name){
        return persistenceService.findByName(name);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<BeerResponse> list(){
        return persistenceService.list();
    }

    @PutMapping("/{name}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBeerStock(@PathVariable("name") String name, @PathVariable("quantity") int quantity) throws BeerAlreadyRegisteredException {
        persistenceService.updateBeerStock(name, quantity);
    }
}
