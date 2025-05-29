package com.example.spum_backend.exception.notFound;

public class RoleNotFoundException extends ResourceNotFoundException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
