package com.hali.spring.trackingapp.controller;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hali.spring.trackingapp.model.LocationData;
import com.hali.spring.trackingapp.service.LocationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController 
{
	private final LocationService service;
	
	@PostMapping("/location/push")
	public void pushLocationData(String data)
	{
		service.pushData(data);
	}
	
	@GetMapping(path = "/location/watch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamLocdata()
	{
		return 	service.watch();
	}
}
