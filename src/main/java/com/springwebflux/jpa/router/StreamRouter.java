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
	RouterFunction<ServerResponse> routes() {
	    return RouterFunctions.route()
	            // Streaming
	            .GET("/stream", streamController::streamHiHandler)
	            .GET("/mono/{name}", streamController::hiMono)

	            // Student read
	            .GET("/getStudents", streamController::getAllStudents)
	            .GET("/getStudentById/{id}", streamController::getStudentById)
	            .GET("/search", streamController::searchStudentsByName)

	            // Student write
	            .POST("/addStudent", streamController::createStudent)
	            .PUT("/updateStudent/{id}", streamController::updateStudent)
                
	            .DELETE("deleteStudent/{id}",streamController::deleteStudent)
	            .build();
	}
}
