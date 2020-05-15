package com.hali.spring.trackingapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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
}
