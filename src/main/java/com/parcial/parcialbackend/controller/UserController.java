package com.parcial.parcialbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final AuthService aunAuthService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getUsers(){
        return ResponseEntity.ok(aunAuthService.allUsers());
    }

    @GetMapping("/get-admis")
    public ResponseEntity<ResponseDTO> getAdmins(){
        return ResponseEntity.ok(aunAuthService.allUsersAdmin());
    }
}
