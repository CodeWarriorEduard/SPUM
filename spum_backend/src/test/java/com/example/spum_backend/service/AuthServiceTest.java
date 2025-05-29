package com.example.spum_backend.service;

import com.example.spum_backend.config.security.services.UserDetailServiceApp;
import com.example.spum_backend.dto.request.user.UserLoginRequestDTO;
import com.example.spum_backend.dto.request.user.UserRegisterRequestDTO;
import com.example.spum_backend.dto.response.TokenResponseDTO;
import com.example.spum_backend.dto.response.UserInfo;
import com.example.spum_backend.entity.Role;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.exception.UserAlreadyRegistered;
import com.example.spum_backend.exception.UserLoginException;
import com.example.spum_backend.mapper.UserMapper;
import com.example.spum_backend.repository.RoleRepository;
import com.example.spum_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailServiceApp userDetailServiceApp;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;

    @Test
    void registerUser_success() {
        // Arrange
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO("Jane", "Smith", "jane@example.com", "pass", RolesEnum.STUDENT);

        Role role = new Role(2L, RolesEnum.STUDENT, Set.of());

        User userToSave = User.builder()
                .userName(dto.getUserName())
                .userLastName(dto.getUserLastName())
                .email(dto.getEmail())
                .password("encoded-password")
                .roles(Set.of(role))
                .build();

        // Simulamos que el user guardado tiene ID (como lo haría la BD)
        User savedUser = User.builder()
                .id(1L)
                .userName(dto.getUserName())
                .userLastName(dto.getUserLastName())
                .email(dto.getEmail())
                .password("encoded-password")
                .roles(Set.of(role))
                .build();

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(roleRepository.findByRole(dto.getRole())).thenReturn(Optional.of(role));
        when(userMapper.toUser(dto)).thenReturn(userToSave);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(userToSave)).thenReturn(savedUser);
        when(userMapper.toUserInfo(any(User.class))).thenReturn(new UserInfo(savedUser.getId(),"Jane", "Smith", "jane@example.com", role.getRole().name()));

        // Act
        UserInfo result = authService.registerUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("STUDENT", result.getRole());

    }


    @Test
    void registerUser_alreadyRegistered_throwsException() {
        // Arrange
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO("Jane", "Smith", "jane@example.com", "pass", RolesEnum.STUDENT);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyRegistered.class, () -> authService.registerUser(dto));
    }


    @Test
    void login_success() {
        UserLoginRequestDTO dto = new UserLoginRequestDTO("user@example.com", "password");
        Role role = new Role();
        role.setId(1L);
        role.setRole(RolesEnum.ADMIN);


        User user = User.builder()
                .id(1L)
                .email(dto.getEmail())
                .password("hashedPassword")
                .roles(Set.of(role))
                .build();



        UserDetails userDetails = mock(UserDetails.class);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), user.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(mock(Authentication.class));
        when(userDetailServiceApp.loadUserByUsername(dto.getEmail()))
                .thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        TokenResponseDTO token = authService.login(dto);

        assertEquals("jwt-token", token.getToken());
        assertEquals("ADMIN", token.getRole().getRole());
    }

    @Test
    void login_wrongPassword_throwsException() {
        UserLoginRequestDTO dto = new UserLoginRequestDTO("user@example.com", "wrong");
        User user = User.builder().email(dto.getEmail()).password("encoded").roles(Set.of(new Role(1L, RolesEnum.ADMIN, Set.of()))).build();

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(UserLoginException.class, () -> authService.login(dto));
    }

    @Test
    void login_userNotFound_throwsException() {
        UserLoginRequestDTO dto = new UserLoginRequestDTO("nouser@example.com", "pass");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(dto));
    }
}
