package com.appexam;

import com.appexam.repository.UserRepository;
import com.appexam.entity.Role;
import com.appexam.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class AppExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppExamApplication.class, args);
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
       return args -> {
           userRepository.save(User.builder()
                   .name("Ndiankou Ndoye")
                   .role(Role.valueOf(String.valueOf(Role.ADMIN)))
                   .username("admin")
                   .inscriptionDate(LocalDate.now())
                   .password(passwordEncoder.encode("passer123")).build());
           userRepository.save(User.builder()
                   .name("Papis Ndoye")
                   .role(Role.valueOf(String.valueOf(Role.USER)))
                   .username("papisndoye")
                   .inscriptionDate(LocalDate.now())
                   .password(passwordEncoder.encode("passer123")).build());

       };

    }

}
