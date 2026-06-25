package com.aamish.auth_service.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aamish.auth_service.dtos.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(RuntimeException.class)
     public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException ex){
    	 return ResponseEntity.badRequest().body(ErrorResponse.builder()
    			 .message(ex.getMessage()).build());
     }
}
