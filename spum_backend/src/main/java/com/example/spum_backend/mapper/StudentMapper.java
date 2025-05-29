package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.StudentUserRegisterRequestDTO;
import com.example.spum_backend.dto.response.StudentResponseDTO;
import com.example.spum_backend.entity.Student;
import com.example.spum_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(StudentUserRegisterRequestDTO dto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "penalties", ignore = true)
    Student toStudent(StudentUserRegisterRequestDTO dto, User user);

    @Mapping(target = "studentName", expression = "java(user.getUserName() + ' ' + user.getUserLastName())")
    StudentResponseDTO toStudentResponseDTO(User user);
}
