package com.hali.spring.trackingapp.service;

import javax.jms.JMSException;

import org.reactivestreams.Publisher;
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
	
	public void pushData(LocationData data)
	{
		jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC, data);

		//		Message<LocationData> msg = MessageBuilder.createMessage(data, null);

		//		jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC, new MessageCreator() {
		//				@Override
		//				public Message createMessage(Session session) throws JMSException {
		//					Message mess = null;
		//
		//					try 
		//					{
		//						mess = session.createTextMessage(objectMapper.writeValueAsString(data));
		//						//mess.setStringProperty("_type", "java.lang.String");
		//						return mess;
		//
		//					} catch (JsonProcessingException e) {
		//						throw new JMSException("boom");
		//					}
		//				}
		//			});

		//		try {
		//			jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC, objectMapper.writeValueAsString(data));
		//		} catch (JmsException | JsonProcessingException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	public Flux<LocationData> watch() {
		return Flux.from(jmsReactiveSource)
                .map(Message::getPayload);
	}

}
