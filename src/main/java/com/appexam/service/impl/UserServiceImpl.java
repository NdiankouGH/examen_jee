package com.appexam.service.impl;

import com.appexam.repository.UserRepository;
import com.appexam.dto.UserDto;
import com.appexam.entity.Role;
import com.appexam.entity.User;
import com.appexam.exception.RequestException;
import com.appexam.mapper.UserMapper;
import com.appexam.service.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class UserServiceImpl implements IUser {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private static  final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto user) {
        try {
            log.info("Inside createUser");
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new RequestException("Username already exists", HttpStatus.BAD_REQUEST);
            }
           User created = new User();
            created.setUsername(user.getUsername());
            created.setPassword(passwordEncoder.encode(user.getPassword()));
            created.setName(user.getName());
            created.setRole(Role.valueOf(user.getRole()));
            created.setInscriptionDate(LocalDate.now());
            return userMapper.toUserDto(userRepository.save(created));

        }catch (Exception e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new RequestException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDto getUser(String username) {
        return null;
    }

    @Override
    public UserDto getUser(int id) {
        return null;
    }

    @Override
    public UserDto editUSer(UserDto user) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }
}
