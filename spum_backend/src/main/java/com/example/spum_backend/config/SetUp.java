package com.example.spum_backend.config;

import com.example.spum_backend.entity.Role;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.repository.RoleRepository;
import com.example.spum_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class SetUp implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleSeeder roleSeeder;

    public SetUp(UserRepository userRepository,
                 RoleRepository roleRepository,
                 PasswordEncoder passwordEncoder,
                 RoleSeeder roleSeeder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleSeeder = roleSeeder;
    }


    @Transactional
    public void createAdmin() {
        if (!userRepository.existsByEmail("admin@spum-backend.com")) {
            Role adminRole = roleRepository.findByRole(RolesEnum.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            User user = User.builder()
                    .userName("admin")
                    .userLastName("admin")
                    .email("admin@spum-backend.com")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleSeeder.seedRolesIfNotExist();
        createAdmin();
    }
}
