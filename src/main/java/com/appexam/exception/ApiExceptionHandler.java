package com.appexam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({RequestException.class})
    public ResponseEntity<ApiException> handleRequestException(RequestException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), ex.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(apiException, ex.status);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
