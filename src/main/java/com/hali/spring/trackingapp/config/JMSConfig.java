package com.hali.spring.trackingapp.config;

import javax.jms.ConnectionFactory;

import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;

import reactor.core.publisher.Flux;


@Configuration
public class JMSConfig 
{
	public static final String Location_TOPIC = "locationtopic";

	//	@Bean
	//	public MessageConverter messageConverter(){
	//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	//		converter.setTargetType(MessageType.TEXT);
	//		converter.setTypeIdPropertyName("_type");
	//		return converter;
	//	}
	
	@Bean
	@Primary
	public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
	        ConnectionFactory connectionFactory,
	        DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory);
	    // This provides all boot's default to this factory, including the
	    // message converter
	    configurer.configure(factory, connectionFactory);
	    // You could still override some of Boot's default if necessary.
	    // As you said if you want to override Boot's defaults or 
	    // values from aplication.properties you have to do it after configurer.configure()
	    factory.setPubSubDomain(true);
	    return factory;
	}

	@Bean
	public JmsTemplate jmsTopicTemplate(
			ConnectionFactory connectionFactory) 
	{
		JmsTemplate template =  new JmsTemplate(connectionFactory);
		template.setConnectionFactory(connectionFactory);
		template.setDefaultDestinationName(JMSConfig.Location_TOPIC);
		//		template.setMessageConverter(messageConverter());
		template.setPubSubDomain(true);
		return template;
	}

	@JmsListener(destination = JMSConfig.Location_TOPIC)
	public void receiveMessage(String mess) {
		System.out.println("from 1: " + mess);
		
	}
	
	@JmsListener(destination = JMSConfig.Location_TOPIC)
	public void receiveMessage2(String mess) 
	{
		System.out.println("from 2: " + mess);
			
	}

	@Bean
	public Publisher<Message<String>> jmsReactiveSource(ConnectionFactory connectionFactory)
	{		
		return IntegrationFlows
				.from(Jms.messageDrivenChannelAdapter(connectionFactory)
						.configureListenerContainer(configurer -> {
							configurer.pubSubDomain(true);
//							configurer.
						} )
						.destination(JMSConfig.Location_TOPIC) 
						.autoStartup(false)
						.id("jmsMessageDrivenChannelAdapter")
						//						 .jmsMessageConverter(messageConverter())
						)
				//.channel(new PublishSubscribeChannel(executor()))					
				.channel(MessageChannels.flux())
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
