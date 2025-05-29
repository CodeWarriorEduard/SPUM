package com.example.spum_backend.mapper;

import com.example.spum_backend.dto.request.user.UserRegisterRequestDTO;
import com.example.spum_backend.dto.request.user.UserUpdateRequestDTO;
import com.example.spum_backend.dto.response.UserInfo;
import com.example.spum_backend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserRegisterRequestDTO dto);




    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "name")
    @Mapping(source = "userLastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "role", expression = "java(user.getRoles().stream().findFirst().orElseThrow().getRole().name())")
    UserInfo toUserInfo(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequestDTO dto, @MappingTarget User user);
}
