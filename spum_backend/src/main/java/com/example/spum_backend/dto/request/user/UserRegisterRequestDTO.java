package com.example.spum_backend.dto.request.user;

import com.example.spum_backend.enumeration.RolesEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank
    String userName;
    @NotBlank
    String userLastName;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
    @NotNull
    RolesEnum role;
}
