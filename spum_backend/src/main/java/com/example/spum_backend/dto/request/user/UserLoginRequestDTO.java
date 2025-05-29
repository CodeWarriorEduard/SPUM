package com.example.spum_backend.dto.request.user;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {
    @NotBlank
    String email;
    @NotBlank
    String password;
}
