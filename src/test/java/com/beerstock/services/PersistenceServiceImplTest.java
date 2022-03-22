package com.beerstock.services;

import com.beerstock.builders.FakeBeer;
import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.excepitons.BeerNameNotFoundException;
import com.beerstock.services.repository.PersistenceRepository;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersistenceServiceImplTest {

    @Mock
    PersistenceRepository persistenceRepository;

    @InjectMocks
    PersistenceServiceImpl persistenceService;

    @Test
    public void shouldCreateANewBeerWhenTheBeerNotExists(){
        BeerRequest fakeBeerRequest = FakeBeer.builder().build().toRequest();
        BeerResponse fakeBeerResponse = FakeBeer.builder().build().toResponse();
        Beer fakeBeer = FakeBeer.builder().build().toModel();

        when(persistenceRepository.findByName(fakeBeerRequest.getName())).thenReturn(Optional.empty());
        when(persistenceRepository.save(any())).thenReturn(fakeBeer);
        BeerResponse beerResponse = persistenceService.save(fakeBeerRequest);

        assertThat(beerResponse.getName(), is(equalTo(fakeBeerResponse.getName())));
        assertThat(beerResponse.getBrand(), is(equalTo(fakeBeerResponse.getBrand())));
        assertThat(beerResponse.getMax(), is(equalTo(fakeBeerResponse.getMax())));
        assertThat(beerResponse.getQuantity(), is(equalTo(fakeBeerResponse.getQuantity())));
        assertThat(beerResponse.getType(), is(equalTo(fakeBeerResponse.getType())));



    }

    @Test
    public void shouldThrowAnExceptionWhenTheBeerAlreadyExists(){
        BeerRequest fakeBeerRequest = FakeBeer.builder().build().toRequest();
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        when(persistenceRepository.findByName(fakeBeerRequest.getName())).thenReturn(Optional.of(fakeBeer));
        assertThrows(BeerNameNotFoundException.class, () -> persistenceService.save(fakeBeerRequest));
    }

    @Test
    public void shouldReturnAllRegisteredBeers(){
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        BeerResponse fakeBeerResponse = FakeBeer.builder().build().toResponse();
        when(persistenceRepository.findAll()).thenReturn(List.of(fakeBeer));
        List<BeerResponse> allBeers = persistenceService.list();
        assertThat(allBeers.get(0).getName(), is(equalTo(fakeBeerResponse.getName())));
        assertThat(allBeers.get(0).getBrand(), is(equalTo(fakeBeerResponse.getBrand())));
        assertThat(allBeers.get(0).getMax(), is(equalTo(fakeBeerResponse.getMax())));
        assertThat(allBeers.get(0).getQuantity(), is(equalTo(fakeBeerResponse.getQuantity())));
    }
}
