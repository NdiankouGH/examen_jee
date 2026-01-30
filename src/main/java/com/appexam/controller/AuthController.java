package com.appexam.controller;

import com.appexam.dto.AuthRequest;
import com.appexam.dto.AuthResponse;
import com.appexam.dto.UserDto;
import com.appexam.entity.RefreshToken;
import com.appexam.entity.User;
import com.appexam.exception.ApiException;
import com.appexam.security.service.CustomUSerDetailsService;
import com.appexam.security.service.JwtUtil;
import com.appexam.security.service.RefreshTokenService;
import com.appexam.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUSerDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;
    private final RefreshTokenService refreshTokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, CustomUSerDetailsService customUserDetailsService, JwtUtil jwtUtil, UserServiceImpl userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto){
        try {

            final UserDto user = userService.createUser(userDto);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDto.getUsername());
           final String token = jwtUtil.generateToken(userDetails);
            final RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponse(token,refreshToken.getToken(),user.getUsername()));

        }catch (ApiException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e) {
            logger.info("Authentication FAILED: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'inscription: " + e.getMessage());
        }catch (Exception e){
            logger.error("Erreur inattendue ");
            logger.error( e.getMessage());

        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final  String token = jwtUtil.generateToken(userDetails);
        final  RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token,refreshToken.getToken(),userDetails.getUsername()));

    }
}
