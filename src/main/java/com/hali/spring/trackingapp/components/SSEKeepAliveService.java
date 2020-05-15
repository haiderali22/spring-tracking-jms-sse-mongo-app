package com.hali.spring.trackingapp.components;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

//@Component
final class SSEKeepAliveService implements SSEService
{

	long delay;

	@Autowired
	SSEKeepAliveService(@Value("${location.sse.keep-alive:#{environment.LOCATION_SSE_KEEP_ALIVE_DELAY ?: 15}}") final Long delay) {
		this.delay = delay;
	}

	@Override
	public <T> Flux<ServerSentEvent<T>> wrap(Flux<T> flux) {
		return Flux.merge(flux.map(t -> ServerSentEvent.builder(t).build()), Flux.interval(Duration.ofSeconds(this.delay)).map(aLong -> ServerSentEvent.<T>builder().comment("keep alive").build()));
	}

}