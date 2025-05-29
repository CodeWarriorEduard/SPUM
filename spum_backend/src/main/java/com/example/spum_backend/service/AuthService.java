package com.example.spum_backend.service;

import com.example.spum_backend.config.security.services.UserDetailServiceApp;
import com.example.spum_backend.dto.request.StudentUserRegisterRequestDTO;
import com.example.spum_backend.dto.request.user.UserLoginRequestDTO;
import com.example.spum_backend.dto.request.user.UserRegisterRequestDTO;
import com.example.spum_backend.dto.response.StudentResponseDTO;
import com.example.spum_backend.dto.response.TokenResponseDTO;
import com.example.spum_backend.dto.response.UserInfo;
import com.example.spum_backend.entity.Role;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.exception.UserAlreadyRegistered;
import com.example.spum_backend.exception.UserLoginException;
import com.example.spum_backend.exception.notFound.RoleNotFoundException;
import com.example.spum_backend.mapper.StudentMapper;
import com.example.spum_backend.mapper.UserMapper;
import com.example.spum_backend.repository.RoleRepository;
import com.example.spum_backend.repository.StudentRepository;
import com.example.spum_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final UserDetailServiceApp userDetailServiceApp;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final StudentMapper studentMapper;

    public StudentResponseDTO registerStudent(StudentUserRegisterRequestDTO dto) {
        validateEmailNotRegistered(dto.getEmail());

        Role role = getRole(RolesEnum.STUDENT);
        User user = studentMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(role));
        userRepository.save(user);

        Student student = studentMapper.toStudent(dto, user);
        studentRepository.save(student);

        return studentMapper.toStudentResponseDTO(user);
    }

    public UserInfo registerUser(UserRegisterRequestDTO dto) {
        validateEmailNotRegistered(dto.getEmail());

        Role role = getRole(dto.getRole());
        User user = userMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return userMapper.toUserInfo(user);
    }

    public TokenResponseDTO login(UserLoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserLoginException("Username or password incorrect");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );


        UserDetails userDetails = userDetailServiceApp.loadUserByUsername(dto.getEmail());

        return new TokenResponseDTO(jwtService.generateToken(userDetails), user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new UserLoginException("No role assigned"))
                .getRole());
    }


    private void validateEmailNotRegistered(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyRegistered("El usuario ya existe");
        }
    }

    private Role getRole(RolesEnum roleEnum) {
        return roleRepository.findByRole(roleEnum)
                .orElseThrow(() -> new RoleNotFoundException("No existe el rol"));
    }
}
