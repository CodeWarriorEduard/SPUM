package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.user.UserRegisterRequestDTO;
import com.example.spum_backend.dto.request.user.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo getUserById(Long id);
    List<UserInfo> getAllUsers();
    UserInfo updateUser(Long id, UserUpdateRequestDTO user);
    void deleteUser(Long id);
}
