package com.jobpilot.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice 
public class ApiExceptionHandler {
    record ErrorBody(String code, String message, List<String> details){}

    @ExceptionHandler (DuplicateEmailException.class)
    public ResponseEntity<ErrorBody> handleDuplicate(DuplicateEmailException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorBody("DUPLICATE_EMAIL",ex.getMessage(),List.of())); 
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorBody> handleValidation(MethodArgumentNotValidException ex){
        List<String> details = ex.getBindingResult().getFieldErrors().stream().map(fe->fe.getField()+": "+fe.getDefaultMessage()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorBody("VALIDATION_ERROR","Request validation failed", details));
        
    }
}
