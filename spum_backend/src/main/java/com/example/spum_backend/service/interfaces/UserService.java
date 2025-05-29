package com.example.spum_backend.service.interfaces;


import com.example.spum_backend.dto.request.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAllUsers();
    UserResponseDTO getOneUserById(Long id);
    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO user);
    UserResponseDTO deleteUser(Long id);
}
