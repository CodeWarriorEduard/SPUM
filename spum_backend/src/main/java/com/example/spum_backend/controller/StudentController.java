package com.example.spum_backend.controller;

import com.example.spum_backend.dto.response.StudentResponseDTO;
import com.example.spum_backend.service.interfaces.StudentService;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(value = "http://localhost:5173")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getStudent() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String email) {
        studentService.deleteStudent(email);
        return ResponseEntity.noContent().build();
    }
}
