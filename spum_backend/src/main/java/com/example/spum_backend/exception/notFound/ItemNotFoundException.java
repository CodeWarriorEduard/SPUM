package com.example.spum_backend.exception.notFound;

public class ItemNotFoundException extends ResourceNotFoundException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
