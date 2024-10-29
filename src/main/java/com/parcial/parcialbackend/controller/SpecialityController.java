package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.DTO.SpecialtyDTO;
import com.parcial.parcialbackend.service.SpecialtyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/speciality")
public class SpecialityController {
    @Autowired
    private final SpecialtyService specialtyService;
    
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody SpecialtyDTO request){
        return ResponseEntity.ok(specialtyService.createEspecialidad(request));
    }
}
