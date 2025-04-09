package com.pm.patient_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice // This annotation is used to handle exceptions globally across all controllers
public class GlobalExceptionHandler {
    private static final   Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {


        log.warn("Email already exists: {}", ex.getMessage());
        Map<String,String>errors=new HashMap<>();
        errors.put("message","Email Already Exist");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFound(PatientNotFoundException ex) {

        log.warn("Patient not found: {}", ex.getMessage());
        Map<String,String>errors=new HashMap<>();
        errors.put("message","Patient Not Found");
        return ResponseEntity.badRequest().body(errors);
    }
}
