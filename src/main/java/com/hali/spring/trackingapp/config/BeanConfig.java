package com.hali.spring.trackingapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.hali.spring.trackingapp.model.LocationData;
import com.hali.spring.trackingapp.service.LocationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.reactivestreams.Publisher;

@Configuration
public class BeanConfig 
{
	@Bean
	public RouterFunction<ServerResponse> htmlRouter(
			@Value("classpath:/static/test.html") Resource html) {
		return route(GET("/test"), request
				-> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
				);
	}
	
	@Bean
	public RouterFunction<?> post(JmsTemplate jmsTopicTemplate) 
	{		
		return route(POST("/location/push"), request -> request.bodyToMono(String.class )
					 .flatMap ( m -> {
						        jmsTopicTemplate.convertAndSend(JMSConfig.Location_TOPIC,m);
						        return Mono.empty();
						   })
					 .flatMap(l -> ServerResponse.ok().body(Mono.empty(), Void.class)));
	}


	@Bean
	public RouterFunction<?> sse(Publisher<Message<String>> jmsReactiveSource,
			JmsMessageDrivenEndpoint	 jmsMessageDrivenChannelAdapter)
	{
		return route(GET("location/watch"),request -> ServerResponse.ok()
									.body(
											Flux.from(jmsReactiveSource)
							                .map(Message::getPayload)
							                .doOnSubscribe(s -> jmsMessageDrivenChannelAdapter.start())
											
//											service.watch()
									.map(o -> ServerSentEvent.builder(o).build()), ServerSentEvent.class ));
	}
}
