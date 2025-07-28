package com.springwebflux.jpa.controller;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class UserController {
    
	
	@GetMapping("/blockingList")
	public List<String> hello() {
		List<String> list = List.of("Hello","Pm","cm","dm","ki","ji","mi","pi");
		
	    return list.stream()
			    .map(str -> {
			        try {
			        	System.out.println(Thread.currentThread().getName());
			            Thread.sleep(2000); 
			        } catch (InterruptedException e) {
			            Thread.currentThread().interrupt(); 
			        }
			        return str.toUpperCase();
			    })
			    .collect(Collectors.toList());
	}
	
	@GetMapping("/webfluxlist")
	public Flux<String> fluxHello() {
		System.out.println(Thread.currentThread().getName());
        List<String> list = List.of("Hello","Pm","cm","dm","ki","ji","mi","pi");
        return Flux.fromIterable(list).map(str->str.toUpperCase()+" ").delayElements(Duration.ofSeconds(2));
	}
}
