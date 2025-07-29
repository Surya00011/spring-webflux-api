package com.springwebflux.jpa.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 @ExceptionHandler(StudentNotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
	                "timestamp", LocalDateTime.now().toString(),
	                "status", HttpStatus.NOT_FOUND.value(),
	                "error", HttpStatus.NOT_FOUND,
	                "message", ex.getMessage()
	        ));
	    }
}
