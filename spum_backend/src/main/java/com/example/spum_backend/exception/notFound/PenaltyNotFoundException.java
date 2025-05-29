package com.example.spum_backend.exception.notFound;

public class PenaltyNotFoundException extends ResourceNotFoundException {
    public PenaltyNotFoundException(String message) {
        super(message);
    }
}
