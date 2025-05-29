package com.example.spum_backend.exception.notFound;

public class ItemTypeNotFoundException extends ResourceNotFoundException {
    public ItemTypeNotFoundException(String message) {
        super(message);
    }
}
