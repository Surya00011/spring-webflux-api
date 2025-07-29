package com.springwebflux.jpa.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.springwebflux.jpa.exception.StudentNotFoundException;
import com.springwebflux.jpa.model.Student;
import com.springwebflux.jpa.repository.StudentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StreamController {

	@Autowired
	StudentRepository studentRepo;

	public Mono<ServerResponse> streamHiHandler(ServerRequest serverRequest) {
		Flux<String> streamFlux = Flux.just("Hello", "hi", "bravo", "flash", "Bean")
				.delayElements(Duration.ofSeconds(2));

		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(streamFlux, String.class);
	}

	public Mono<ServerResponse> hiMono(ServerRequest serverRequest) {
		String msgString = "Hii " + serverRequest.pathVariable("name");
		Mono<String> publisherMono = Mono.just(msgString);
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(publisherMono, String.class);
	}

	public Mono<ServerResponse> getAllStudents(ServerRequest request) {
		Flux<Student> studentsFlux = studentRepo.findAll().delayElements(Duration.ofSeconds(2));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(studentsFlux, Student.class);
	}

	public Mono<ServerResponse> getStudentById(ServerRequest request) {
		Integer id;
		try {
			id = Integer.valueOf(request.pathVariable("id"));
		} catch (NumberFormatException e) {
			return ServerResponse.badRequest().bodyValue("Invalid ID format");
		}

		return studentRepo.findById(id).switchIfEmpty(Mono.error(new StudentNotFoundException(id)))
				.flatMap(student -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(student));
	}

	public Mono<ServerResponse> searchStudentsByName(ServerRequest request) {
		String name = request.queryParam("name").orElse("");

		if (name.isBlank()) {
			return ServerResponse.badRequest().bodyValue("Missing or empty 'name' query parameter");
		}

		Flux<Student> studentsFlux = studentRepo.findByNameStartingWithIgnoreCase(name);

		return studentsFlux.hasElements().flatMap(hasData -> {
			if (!hasData) {
				throw new StudentNotFoundException("Student not found with name " + name);
			}
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentsFlux, Student.class);
		});
	}

	public Mono<ServerResponse> createStudent(ServerRequest request) {
		return request.bodyToMono(Student.class).flatMap(student -> {
			if (student.name() == null) {
				return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
						.bodyValue("Name field is missing");
			} else if (student.name().isBlank()) {
				return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
						.bodyValue("Name must not be blank");
			}
			return studentRepo.save(student).flatMap(
					saved -> ServerResponse.status(201).contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
		});
	}

	public Mono<ServerResponse> updateStudent(ServerRequest request) {
		int id = Integer.parseInt(request.pathVariable("id"));

		return studentRepo.findById(id)
				.flatMap(existingStudent -> request.bodyToMono(Student.class)
						.map(updated -> new Student(id, updated.name())) 
						.flatMap(studentRepo::save))
				.flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updated))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> deleteStudent(ServerRequest request) {
	    int id = Integer.parseInt(request.pathVariable("id"));

	    return studentRepo.findById(id)
	            .flatMap(existing ->
	                studentRepo.delete(existing)
	                        .then(ServerResponse.noContent().build()) 
	            )
	            .switchIfEmpty(ServerResponse.notFound().build());
	}


}
