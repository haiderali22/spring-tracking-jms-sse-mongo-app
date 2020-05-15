package com.hali.spring.trackingapp.config;

import javax.jms.ConnectionFactory;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.hali.spring.trackingapp.model.LocationData;


@Configuration
public class JMSConfig 
{
	public static final String Location_TOPIC = "locationtopic";

	@Bean
	public MessageConverter messageConverter(){
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}


	//	@Bean
	//	public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(
	//	        DefaultJmsListenerContainerFactoryConfigurer configurer,
	//	        ConnectionFactory connectionFactory) 
	//	{
	//	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	//	    configurer.configure(factory, connectionFactory);
	//	    factory.setPubSubDomain(true); 
	//	    factory.setMessageConverter(messageConverter());
	//	    return factory;
	//	}
	//	
	@Bean
	public JmsTemplate jmsTopicTemplate(
			ConnectionFactory connectionFactory) 
	{
		JmsTemplate template =  new JmsTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		template.setPubSubDomain(true);
		return template;
	}

	@Bean
	public Publisher<Message<LocationData>> jmsReactiveSource(ConnectionFactory connectionFactory) {

		return IntegrationFlows
				.from(Jms.messageDrivenChannelAdapter(connectionFactory)
						.destination(JMSConfig.Location_TOPIC)
						 .autoStartup(false)
		                 .id("jmsMessageDrivenChannelAdapter")
						 .jmsMessageConverter(messageConverter()))
				//.channel(new PublishSubscribeChannel(executor()))					
				//.channel(MessageChannels.flux())
				//.channel(MessageChannels.queue())
				
				
				.toReactivePublisher();
	}

//	@Bean
//	public ThreadPoolTaskExecutor executor() {
//
//		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
//		pool.setCorePoolSize(10);
//		pool.setMaxPoolSize(10);
//		pool.setWaitForTasksToCompleteOnShutdown(true);
//		return pool;
//	}

}
