package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.parcial.parcialbackend.DTO.PacientDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.PacientService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pacient")
public class PacientController {
    @Autowired
    private final PacientService pacientService;
    
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody PacientDTO request){
        return ResponseEntity.ok(pacientService.createPacient(request));
    }
}
