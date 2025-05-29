package com.example.spum_backend.exception.notFound;

public class UserNotFoundException extends ResourceNotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
