package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.Role;
import com.example.LibrarySpring.model.Status;
import com.example.LibrarySpring.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    @InheritInverseConfiguration
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserDTO userDTO);
    @AfterMapping
    default void setStatusAndRole(@MappingTarget User user) {
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER_ROLE);
    }
}
