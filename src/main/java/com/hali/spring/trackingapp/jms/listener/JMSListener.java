package com.hali.spring.trackingapp.jms.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hali.spring.trackingapp.config.JMSConfig;
import com.hali.spring.trackingapp.model.LocationData;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
//@Component
public class JMSListener
{
//	private final ObjectMapper objectMapper;
	
	@JmsListener(destination = JMSConfig.Location_TOPIC,containerFactory = "jmsTopicListenerContainerFactory")
	@Bean
	public Flux<LocationData> receiveTopicMessage(@Payload LocationData message,@Headers MessageHeaders headers )
	{
//		LocationData data = objectMapper.convertValue(message, LocationData.class);
		
		return  Flux.just(message);
	}
}
