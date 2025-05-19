package com.example.spum_backend.service;

import com.example.spum_backend.dto.request.StudentUserRegisterRequestDTO;
import com.example.spum_backend.dto.request.UserLoginRequestDTO;
import com.example.spum_backend.dto.request.UserRegisterRequestDTO;
import com.example.spum_backend.dto.response.TokenResponseDTO;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.entity.User;
import com.example.spum_backend.enumeration.RolesEnum;
import com.example.spum_backend.exception.UserLoginException;
import com.example.spum_backend.repository.StudentRepository;
import com.example.spum_backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void registerStudent(StudentUserRegisterRequestDTO student){

        User user = User
                .builder()
                .userName(student.firstName())
                .userLastName(student.lastName())
                .email(student.email())
                .password(passwordEncoder.encode(student.password()))
                .role(RolesEnum.STUDENT)
                .build();

        // Saving the user

        userRepository.save(user);

        Student studenToRegister = Student
                .builder()
                .studentCollegeId(student.studentCollegeId())
                .user(user)
                .penalties(List.of())
                .build();

        studentRepository.save(studenToRegister);

    }


    public void registerUser(UserRegisterRequestDTO user){

        User userToRegister = User
                .builder()
                .userName(user.name())
                .userLastName(user.lastName())
                .email(user.email())
                .password(passwordEncoder.encode(user.password()))
                .role(user.role())
                .build();

        userRepository.save(userToRegister);
    }

    public TokenResponseDTO login(UserLoginRequestDTO loginDetails){

        String email = loginDetails.email();
        String password = loginDetails.password();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        System.out.println(user.getAuthorities());

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new UserLoginException("Username or password incorrect");
        }


        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    email,
                    password
            )
        );

        return new TokenResponseDTO(jwtService.getToken(user));

    }





}
