    package com.example.spum_backend.dto.response;


    import com.example.spum_backend.enumeration.RolesEnum;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.management.relation.Role;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TokenResponseDTO{
        String token;
        RolesEnum role;
    }
