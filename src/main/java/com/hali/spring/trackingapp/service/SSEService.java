package com.hali.spring.trackingapp.service;

import javax.annotation.PostConstruct;

import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hali.spring.trackingapp.model.LocationData;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

//@Service
@RequiredArgsConstructor
public class SSEService 
{	
//	private final JmsListenerContainerFactory<?> jmsListenerContainerFactory;
//	private final ObjectMapper  mapper;
//	
//	private Flux<LocationData>  cloneFlux;	
//	
//	
//	@PostConstruct
//	private void init()
//	{
//		
//		Flux<LocationData> flux =  container.receive(topic).map( m -> m.getMessage())
//		          .map( m -> {
//		        	  			try {
//									return mapper.readValue(m ,LocationData.class);
//								} catch (JsonProcessingException e) {
//									return null;
//								}
//		        	  });
//		
//		cloneFlux = flux.share();
//		
////		 Flux<LocationData> flux = Flux.create( fluxSink -> {
////					 
////			 container.
////			 
////			 container.addMessageListener(
////					 new MessageListenerAdapter(new RedisMessageSubscriber(fluxSink,mapper))
////					 ,topic	);
////			 
////			 	
////			 
////		 });
//	}
//	
//	public Flux<LocationData> getSSE()	{
//		
//		return cloneFlux;
//	}

}
