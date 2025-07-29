package com.springwebflux.jpa.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.springwebflux.jpa.model.Student;

import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer>{
	Flux<Student> findByNameStartingWithIgnoreCase(String prefix);
}
