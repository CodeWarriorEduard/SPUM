package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserResponseDTO;
import com.example.spum_backend.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @PatchMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return userService.updateUser(id, userUpdateRequestDTO);
    }


    @DeleteMapping("/{id}")
    public UserResponseDTO deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

}
