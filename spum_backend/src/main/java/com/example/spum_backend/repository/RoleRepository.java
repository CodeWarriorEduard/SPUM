package com.example.spum_backend.repository;

import com.example.spum_backend.entity.Role;
import com.example.spum_backend.enumeration.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RolesEnum role);
}
