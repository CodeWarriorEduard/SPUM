package com.example.spum_backend.dto.request.user;

import com.example.spum_backend.enumeration.RolesEnum;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    String userName;
    String userLastName;
    @Email
    String email;
    String password;
    RolesEnum role;

}
