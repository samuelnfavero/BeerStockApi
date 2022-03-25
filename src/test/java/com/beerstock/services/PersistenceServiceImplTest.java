package com.beerstock.services;

import com.beerstock.builders.FakeBeer;
import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.excepitons.BeerAlreadyRegisteredException;
import com.beerstock.excepitons.BeerNotFoundException;
import com.beerstock.excepitons.StockMaxCapacityException;
import com.beerstock.services.repository.PersistenceRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    ArgumentCaptor<Beer> beerCaptor;

    @Mock
    PersistenceRepository persistenceRepository;

    @InjectMocks
    PersistenceServiceImpl persistenceService;

    @Test
    public void shouldCreateANewBeerWhenTheBeerNotExists() throws BeerAlreadyRegisteredException {
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
        assertThrows(BeerNotFoundException.class, () -> persistenceService.save(fakeBeerRequest));
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

    @Test
    public void shouldReturnABeerWhenReceiveAName(){
        String name = "Brahma";
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        when(persistenceRepository.findByName(name)).thenReturn(Optional.of(fakeBeer));
        BeerResponse beer = persistenceService.findByName(name);

        assertThat(beer.getId(), is(equalTo(fakeBeer.getId())));
        assertThat(beer.getName(), is(equalTo(fakeBeer.getName())));
        assertThat(beer.getBrand(), is(equalTo(fakeBeer.getBrand())));
        assertThat(beer.getMax(), is(equalTo(fakeBeer.getMax())));
        assertThat(beer.getQuantity(), is(equalTo(fakeBeer.getQuantity())));
        assertThat(beer.getType().getType(), is(equalTo(fakeBeer.getType().getType())));
    }

    @Test
    public void shouldThrowAnExceptionWhenBeerDoNotExists(){
        String name = "Brahma";
        assertThrows(BeerNotFoundException.class, () -> persistenceService.findByName(name));
    }

    @Test
    public void shouldUpdateTheQuantityWhenValidationsSuceed(){
        Beer originalFakeBeer = FakeBeer.builder().build().toModel();
        Beer updatedFakeBeer = FakeBeer.builder().build().toModel();
        int quantity = 20;

        when(persistenceRepository.findByName(any())).thenReturn(Optional.of(updatedFakeBeer));
        persistenceService.updateBeerStock(updatedFakeBeer.getName(), quantity);
        assertThat(updatedFakeBeer.getQuantity(), is(equalTo(originalFakeBeer.getQuantity() + quantity)));
    }

    @Test
    public void shouldThrowsAnExceptionWhenTheBeerDoNotExists(){
        String fakeMessage = "Cerveja de nome Brahma não encontrada";
        String beerName = "Brahma";
        int quantity = 20;

        Exception errorMessage = assertThrows(BeerNotFoundException.class, () -> persistenceService.updateBeerStock(beerName, quantity));
        assertThat(errorMessage.getMessage(), is(equalTo(fakeMessage)));
    }

    @Test
    public void shouldThrowsAnExceptionWhenExceedThaMaxQuantityOfBeers(){
        String fakeMessage = "Quantidade máxima do estoque excedida em 5. O valor máximo a ser inserido é de 20.";
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        int quantity = 25;

        when(persistenceRepository.findByName(any())).thenReturn(Optional.of(fakeBeer));
        Exception errorMessage = assertThrows(StockMaxCapacityException.class,
                () -> persistenceService.updateBeerStock(fakeBeer.getName(), quantity));
        assertThat(errorMessage.getMessage(), is(equalTo(fakeMessage)));
    }
}
