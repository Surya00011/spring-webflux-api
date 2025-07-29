package com.springwebflux.jpa.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springwebflux.jpa.controller.StreamController;

@Configuration
public class StreamRouter {

    private final StreamController streamController;

    public StreamRouter(StreamController streamController) {
        this.streamController = streamController;
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions
                .route(RequestPredicates.GET("/stream"), streamController::streamHiHandler)
                .andRoute(RequestPredicates.GET("/mono/{name}"), streamController::hiMono);
    }
}
