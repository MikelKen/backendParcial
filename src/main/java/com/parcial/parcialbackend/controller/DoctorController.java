package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.DoctorDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.DoctorService;


import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private final DoctorService doctorService;
    
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody DoctorDTO request){
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }
}
