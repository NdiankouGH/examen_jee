package com.appexam.security.service;

import com.appexam.repository.UserRepository;
import com.appexam.exception.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUSerDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUSerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                ()->   new EntityNotFoundException("User not found with username: " + username)
        );
    }
}
