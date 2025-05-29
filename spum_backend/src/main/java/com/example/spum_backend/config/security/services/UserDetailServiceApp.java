package com.example.spum_backend.config.security.services;

import com.example.spum_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceApp implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceApp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserInfoDetail::new)  // <- esta es la conversión correcta
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
