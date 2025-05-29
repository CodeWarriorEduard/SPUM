package com.example.spum_backend.exception.notFound;

public class PenaltyTypeNotFoundException extends ResourceNotFoundException {
    public PenaltyTypeNotFoundException(String message) {
        super(message);
    }
}
