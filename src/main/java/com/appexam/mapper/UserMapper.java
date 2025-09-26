package com.appexam.mapper;

import com.appexam.dto.UserDto;
import com.appexam.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserDto(UserDto userDto);

    UserDto toUserDto(User user);
}
