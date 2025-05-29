package com.example.spum_backend.dto.response;

import com.example.spum_backend.enumeration.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String role;
}
