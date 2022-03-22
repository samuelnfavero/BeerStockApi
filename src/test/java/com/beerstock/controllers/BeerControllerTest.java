package com.beerstock.controllers;

import com.beerstock.builders.FakeBeer;
import com.beerstock.dtos.request.BeerRequest;
import com.beerstock.dtos.response.BeerResponse;
import com.beerstock.services.PersistenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
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
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(fakeBeerResponse.getId().intValue()))))
                .andExpect(jsonPath("$.name", Matchers.is(Matchers.equalTo(fakeBeerResponse.getName()))))
                .andExpect(jsonPath("$.brand", Matchers.is(Matchers.equalTo(fakeBeerResponse.getBrand()))))
                .andExpect(jsonPath("$.max", Matchers.is(Matchers.equalTo(fakeBeerResponse.getMax()))))
                .andExpect(jsonPath("$.quantity", Matchers.is(Matchers.equalTo(fakeBeerResponse.getQuantity()))))
                .andExpect(jsonPath("$.type", Matchers.is(Matchers.equalTo(fakeBeerResponse.getType().toString()))));
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
}
