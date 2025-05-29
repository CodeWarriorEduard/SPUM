package com.example.spum_backend.exception.notFound;

public class StudentNotFoundException extends ResourceNotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}