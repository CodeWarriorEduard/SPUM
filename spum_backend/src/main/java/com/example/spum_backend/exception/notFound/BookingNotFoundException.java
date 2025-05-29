package com.example.spum_backend.exception.notFound;

public class BookingNotFoundException extends ResourceNotFoundException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
