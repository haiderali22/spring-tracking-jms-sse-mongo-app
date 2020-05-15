package com.hali.spring.trackingapp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hali.spring.trackingapp.model.LocationData;


@SpringBootTest
class LocationServiceTest 
{
	@Autowired
	LocationService service;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testPushData() 
	{
//		service.pushData(LocationData.builder().id("11").lat(22.22).lng(77.77).build());
	}
	
	
}
