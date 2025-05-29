package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.user.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserInfo;
import com.example.spum_backend.entity.Role;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.exception.UserAlreadyRegistered;
import com.example.spum_backend.exception.notFound.BookingNotFoundException;
import com.example.spum_backend.exception.notFound.RoleNotFoundException;
import com.example.spum_backend.exception.notFound.UserNotFoundException;
import com.example.spum_backend.mapper.UserMapper;
import com.example.spum_backend.repository.RoleRepository;
import com.example.spum_backend.repository.UserRepository;
import com.example.spum_backend.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserInfo getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserInfo)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserInfo).toList();
    }

    @Override
    public UserInfo updateUser(Long id, UserUpdateRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserAlreadyRegistered("Email already in use");
            }
        }

        userMapper.updateEntityFromDto(request, user);

        if (request.getRole() != null) {
            Role newRole = roleRepository.findByRole(request.getRole())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found: " + request.getRole()));
            user.setRoles(new HashSet<>(Set.of(newRole)));
        }


        return userMapper.toUserInfo(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con id: " + id));

        userRepository.delete(user);
    }
}
