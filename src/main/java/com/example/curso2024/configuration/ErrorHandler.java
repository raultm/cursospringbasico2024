package com.example.curso2024.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;



import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class ErrorHandler {


    public static NoSuchElementException createNoSuchElementException(String recurso, Long id){
        return new NoSuchElementException(String.format("%s: %d no existe", recurso, id));
    }

    public static NoSuchElementException createNoSuchElementException(String recurso, String value){
        return new NoSuchElementException(String.format("%s: %s no existe", recurso, value));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> generateResourceNotFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>(ApiError.builder().code(HttpStatus.NOT_FOUND.value()).error(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> generateSqlExceptionHelper(DataIntegrityViolationException  ex) {
        return new ResponseEntity<>(ApiError.builder().code(HttpStatus.BAD_REQUEST.value()).error(getRootCauseMessage(ex)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> httpMessageNotReadable(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ApiError.builder().code(HttpStatus.BAD_REQUEST.value()).error(ex.getMostSpecificCause().getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
  
  
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
                ApiError.builder().code(HttpStatus.BAD_REQUEST.value()).error(errors).build(), 
                HttpStatus.BAD_REQUEST
            );
    }

    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();
        if(cause == null) {
            return "DataIntegrityViolation";
        }
        return cause.getMessage();
    }

    @Data
    @Builder
    public static class ApiError {
        
        private int code;
        private Object error;
        
    }

}