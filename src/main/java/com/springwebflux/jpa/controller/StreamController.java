package com.springwebflux.jpa.controller;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StreamController {

    public Mono<ServerResponse> streamHiHandler(ServerRequest serverRequest) {
        Flux<String> streamFlux = Flux
                .just("Hello", "hi", "bravo", "flash", "Bean")
                .delayElements(Duration.ofSeconds(2));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(streamFlux, String.class);
    }
    
    public Mono<ServerResponse> hiMono(ServerRequest serverRequest){
    	String msgString = "Hii "+serverRequest.pathVariable("name");
    	Mono<String> publisherMono = Mono.just(msgString);
    	return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(publisherMono,String.class);
    }
}
