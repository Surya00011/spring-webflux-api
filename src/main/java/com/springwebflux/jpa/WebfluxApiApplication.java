package com.springwebflux.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfluxApiApplication {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		SpringApplication.run(WebfluxApiApplication.class, args);
	}

}
