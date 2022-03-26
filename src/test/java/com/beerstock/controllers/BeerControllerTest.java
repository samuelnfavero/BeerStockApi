package com.beerstock.controllers;

import com.beerstock.builders.FakeBeer;
import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.entities.Beer;
import com.beerstock.services.PersistenceService;
import com.beerstock.services.repository.PersistenceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Mock
    PersistenceRepository persistenceRepository;
    @Mock
    PersistenceService persistenceService;
    @InjectMocks
    BeerController beerController;

    @Test
    public void shouldCreateANewBeerWhenReceiveAHttpRequest() throws Exception {
        BeerRequest fakeBeerRequest = FakeBeer.builder().build().toRequest();
        BeerResponse fakeBeerResponse = FakeBeer.builder().build().toResponse();
        String jsonRequest = mapper.writeValueAsString(fakeBeerRequest);
        mockMvc.perform(
                post("/beerstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(equalTo(fakeBeerResponse.getId().intValue()))))
                .andExpect(jsonPath("$.name", is(equalTo(fakeBeerResponse.getName()))))
                .andExpect(jsonPath("$.brand", is(equalTo(fakeBeerResponse.getBrand()))))
                .andExpect(jsonPath("$.max", is(equalTo(fakeBeerResponse.getMax()))))
                .andExpect(jsonPath("$.quantity", is(equalTo(fakeBeerResponse.getQuantity()))))
                .andExpect(jsonPath("$.type", is(equalTo(fakeBeerResponse.getType().toString()))));
    }


    @Test
    public void shouldThrowAnExceptionWhenValidationFails() throws Exception {
        BeerRequest fakeBeerRequest = FakeBeer.builder().build().toRequest();
        fakeBeerRequest.setName(null);
        String jsonRequest = mapper.writeValueAsString(fakeBeerRequest);

        mockMvc.perform(
                post("/beerstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAListOfAllBeersWhenReceiveAGetHttpRequest() throws Exception {
        BeerResponse fakeBeerResponse = FakeBeer.builder().build().toResponse();
        Beer fakeBeer = FakeBeer.builder().build().toModel();




        mockMvc.perform(
                get("/beerstock")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isFound());
    }

    @Test
    public void shouldReturnABeerWhenReceiveAName() throws Exception { //NÃO FUNCIONA. ESTÁ IGNORANDO O COMANDO WHEN
        BeerResponse fakeBeerResponse = FakeBeer.builder().build().toResponse();
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        String jsonBeer = mapper.writeValueAsString(fakeBeerResponse);
        when(persistenceRepository.findByName((fakeBeerResponse.getName()))).thenReturn(Optional.of(fakeBeer));
//        when(persistenceService.findByName(fakeBeerResponse.getName())).thenReturn(fakeBeerResponse);
        mockMvc.perform(
                get("/beerstock/" + fakeBeerResponse.getName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBeer)

        )
                .andExpect(status().isFound());
    }

    @Test
    public void shouldReturnaHttpStatusOKWhenReceiveAPutHttpRequest() throws Exception {//MESMO ERRO DE CIMA
        Beer fakeBeer = FakeBeer.builder().build().toModel();
        int quantity = 20;

        when(persistenceRepository.findByName(any())).thenReturn(Optional.of(fakeBeer));

        mockMvc.perform(
                put("/beerstock/" + fakeBeer.getName() + "/" + quantity)
        )
                .andExpect(status().isOk());
    }
}
