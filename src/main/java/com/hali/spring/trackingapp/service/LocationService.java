package com.hali.spring.trackingapp.service;

import javax.jms.JMSException;

import org.reactivestreams.Publisher;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.integration.jms.dsl.JmsMessageDrivenChannelAdapterSpec;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hali.spring.trackingapp.config.JMSConfig;
import com.hali.spring.trackingapp.model.LocationData;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class LocationService 
{
	private final JmsTemplate jmsTopicTemplate;
//	private final ObjectMapper objectMapper;
	private final  Publisher<Message<LocationData>> jmsReactiveSource;
	private final JmsMessageDrivenEndpoint	 jmsMessageDrivenChannelAdapter;
	
	public void pushData(LocationData data)
	{
		jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC, data);

	}

	public Flux<LocationData> watch() {
		return Flux.from(jmsReactiveSource)
                .map(Message::getPayload)
                .doOnSubscribe(s -> jmsMessageDrivenChannelAdapter.start());
	}

}
