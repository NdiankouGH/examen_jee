package com.appexam.service;

import com.appexam.dto.UserDto;
import com.appexam.entity.User;

public interface IUser {

    UserDto createUser(UserDto user) ;
    UserDto getUser(String username) ;
    UserDto getUser(int id) ;
    UserDto editUSer(UserDto user) ;
    void deleteUser(int id) ;

}
