package com.example.spum_backend.exception;

import com.example.spum_backend.exception.notFound.ResourceNotFoundException;

public class BookingConflict extends RuntimeException {
    public BookingConflict(String message) {
        super(message);
    }

    public static class StudentNotFoundException extends ResourceNotFoundException {
        public StudentNotFoundException(String message) {
            super(message);
        }
    }
}
