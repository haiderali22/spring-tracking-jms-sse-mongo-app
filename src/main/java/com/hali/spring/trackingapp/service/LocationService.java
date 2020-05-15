package com.hali.spring.trackingapp.service;

import org.reactivestreams.Publisher;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

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
	private final  Publisher<Message<String>> jmsReactiveSource;
	private final JmsMessageDrivenEndpoint	 jmsMessageDrivenChannelAdapter;
	
	public void pushData(String data)
	{
		jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC, data);

	}

	public Flux<String> watch() {
		return Flux.from(jmsReactiveSource)
                .map(Message::getPayload)
                .doOnSubscribe(s -> jmsMessageDrivenChannelAdapter.start());
	}

}
