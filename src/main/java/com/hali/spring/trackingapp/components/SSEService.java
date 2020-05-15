package com.hali.spring.trackingapp.components;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface SSEService {

  <T> Flux<ServerSentEvent<T>> wrap(Flux<T> flux);

}