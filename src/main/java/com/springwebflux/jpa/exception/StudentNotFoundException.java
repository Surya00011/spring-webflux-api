package com.springwebflux.jpa.exception;

public class StudentNotFoundException extends RuntimeException {
	public StudentNotFoundException(Integer id) {
		super("Student not found with ID: " + id);
	}

	public StudentNotFoundException(String message) {
		super(message);
	}
}
