package com.greenfox.backend.mapper;

import com.greenfox.backend.dto.UserDto;
import com.greenfox.backend.dto.UserCreateDto;
import com.greenfox.backend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "role", ignore = true) // иначе MapStruct ругается
    User fromCreateDto(UserCreateDto dto);
}
