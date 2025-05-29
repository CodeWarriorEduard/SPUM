package com.example.spum_backend.service.impl;

import com.example.spum_backend.dto.response.StudentResponseDTO;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.exception.BookingConflict;
import com.example.spum_backend.exception.notFound.StudentNotFoundException;
import com.example.spum_backend.repository.StudentRepository;
import com.example.spum_backend.service.interfaces.StudentService;
import com.example.spum_backend.service.interfaces.internal.StudentServiceEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService, StudentServiceEntity {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public void deleteStudent(String email) {
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + email));

        studentRepository.delete(student);
    }

    @Override
    public Optional<StudentResponseDTO> getStudent(String studentId) {
        return Optional.empty();
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        return List.of();
    }


    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new BookingConflict.StudentNotFoundException("Student not found"));
    }


    @Override
    public List<Student> getAllStudentsEntities() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudentByEmail(String email) {
        return studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new BookingConflict.StudentNotFoundException("Student not found"));
    }
}
