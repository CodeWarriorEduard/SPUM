package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.PenaltyRequestDTO;
import com.example.spum_backend.dto.response.PenaltyResponseDTO;
import com.example.spum_backend.entity.Penalty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PenaltyMapper {

    @Mapping(source = "type.penaltyTypeId", target = "penaltyType")
    @Mapping(target = "studentName", expression = "java(penalty.getStudent().getUser().getUserName() + \" \" + penalty.getStudent().getUser().getUserLastName())")
    @Mapping(source = "student.user.email", target = "email")
    PenaltyResponseDTO toDTO(Penalty penalty);

}
