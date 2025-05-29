package com.example.spum_backend.config;

import com.example.spum_backend.entity.Role;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;

    public void seedRolesIfNotExist() {
        for (RolesEnum roleEnum : RolesEnum.values()) {
            roleRepository.findByRole(roleEnum)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleEnum, Set.of())));
        }
    }
}
