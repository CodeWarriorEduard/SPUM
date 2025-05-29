package com.example.spum_backend.controller;

import com.example.spum_backend.dto.request.StudentUserRegisterRequestDTO;
import com.example.spum_backend.dto.request.user.UserLoginRequestDTO;
import com.example.spum_backend.dto.request.user.UserRegisterRequestDTO;
import com.example.spum_backend.dto.response.StudentResponseDTO;
import com.example.spum_backend.dto.response.TokenResponseDTO;
import com.example.spum_backend.dto.response.UserInfo;
import com.example.spum_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register-student")
    public ResponseEntity<StudentResponseDTO> registerStudent(@RequestBody @Valid StudentUserRegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerStudent(request));
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserInfo> registerUser(@RequestBody @Valid UserRegisterRequestDTO userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDetails));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO loginDetails){
        return ResponseEntity.ok(authService.login(loginDetails));
    }
}
