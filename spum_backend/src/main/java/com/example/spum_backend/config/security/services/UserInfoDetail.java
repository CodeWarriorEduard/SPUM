package com.example.spum_backend.config.security.services;

import com.example.spum_backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoDetail implements UserDetails {

    private final Long id;

    private final String userName;

    private final String userLastName;

    private final String password;

    private final String email;

    private final Set<GrantedAuthority> authorities;

    public UserInfoDetail(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.userLastName = user.getUserLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRole()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
