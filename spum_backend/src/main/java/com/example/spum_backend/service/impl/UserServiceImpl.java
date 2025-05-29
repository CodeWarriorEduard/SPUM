package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.request.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserResponseDTO;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.exception.UserNotFoundException;
import com.example.spum_backend.repository.UserRepository;
import com.example.spum_backend.service.interfaces.UserService;
import com.example.spum_backend.service.interfaces.internal.UserServiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceEntity, UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id "+id+" not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        return getAllUsers().stream()
                .map((user) -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getOneUserById(Long id) {
        return modelMapper.map(getUserById(id), UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO user) {

        User userToUpdate = getUserById(id);

        if(!user.getEmail().isBlank()){
            userToUpdate.setEmail(user.getActualEmail());
        }
        if(!user.getPassword().isBlank()){
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (!user.getRole().isBlank()) {
            userToUpdate.setRole(RolesEnum.valueOf(user.getRole()));
        }

        if(!user.getUserFirstName().isBlank()){
            userToUpdate.setUserFirstName(user.getUserFirstName());
        }
        if(!user.getUserLastName().isBlank()){
            userToUpdate.setUserLastName(user.getUserLastName());
        }


        userRepository.save(userToUpdate);

        return modelMapper.map(userToUpdate, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
