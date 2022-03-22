package com.beerstock.controllers;


import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
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
    public BeerResponse save(@RequestBody @Valid BeerRequest beerRequest){
        return persistenceService.save(beerRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BeerResponse> list(){
        return persistenceService.list();
    }
}
