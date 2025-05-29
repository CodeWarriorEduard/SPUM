package com.example.spum_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateRequestDTO {

    String actualEmail;
    String userFirstName;
    String userLastName;
    String password;
    String email;
    String role;

}
