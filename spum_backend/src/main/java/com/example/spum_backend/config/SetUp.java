package com.example.spum_backend.config;

import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SetUp implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetUp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAdmin(){
        if(!userRepository.existsByEmail("admin@spum-backend.com")){
            User user = User.builder()
                    .userFirstName("admin")
                    .userLastName("admin")
                    .email("admin@spum-backend.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(RolesEnum.ADMIN)
                    .build();
                userRepository.save(user);
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createAdmin();
    }
}
