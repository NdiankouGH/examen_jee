package com.appexam.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException {
    String message;
    HttpStatus status;
    LocalDateTime timestamp;

}
