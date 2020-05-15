package com.hali.spring.trackingapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.hali.spring.trackingapp.model.LocationData;
import com.hali.spring.trackingapp.service.LocationService;

import reactor.test.StepVerifier;

@SpringBootTest
class LocationControllerTest 
{
	@Autowired
	LocationService service;
	
	WebTestClient client;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		 client = WebTestClient.bindToController(new LocationController(service)).build();
	}

	@Test
	void testStreamLocdata() {
		
		//fix the test
		
		FluxExchangeResult<LocationData> result = client.get().uri("/location/watch")
		        .accept(MediaType.TEXT_EVENT_STREAM)
		        .exchange()
		        .expectStatus().isOk()
		        .returnResult(LocationData.class);
		
		
		
		LocationData data = LocationData.builder().id("11").lat(22.22).lng(77.77).build();
		
		 client.post().uri("/location/push").body(data, LocationData.class);
		 
		 StepVerifier.create(result.getResponseBody())
			.consumeNextWith(d ->
				{	System.out.println(d);}
			);
		 
	}

}
