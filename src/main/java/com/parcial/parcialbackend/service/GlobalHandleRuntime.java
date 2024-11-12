package com.parcial.parcialbackend.service;

import com.parcial.parcialbackend.DTO.ResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalHandleRuntime {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> handleRuntimeException(RuntimeException ex){
        
        ResponseDTO response = ResponseDTO.builder()
                .data(null)
                .success(false)
                .error(true)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
