package com.example.spum_backend.service.interfaces;

import com.example.spum_backend.dto.request.StudentUserRegisterRequestDTO;
import com.example.spum_backend.dto.response.StudentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    void deleteStudent(String email);
    Optional<StudentResponseDTO> getStudent(String studentId);
    List<StudentResponseDTO> getAllStudents();
}
