package com.example.spum_backend.service.interfaces.internal;

import com.example.spum_backend.entity.User;

import java.util.List;

public interface UserServiceEntity {

    List<User> getAllUsers();
    User getUserById(Long id);
    void deleteUserById(Long id);

}
