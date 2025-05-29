package com.example.spum_backend.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUserRegisterRequestDTO{
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
    Long studentCollegeId;
}
