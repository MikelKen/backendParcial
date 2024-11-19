package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parcial.parcialbackend.DTO.PrescriptionDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.PrescriptionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prescription")
public class PrescriptionController {
    
    @Autowired
    private final PrescriptionService prescriptionService;
    @PostMapping("/create") 
    public ResponseEntity<ResponseDTO> create(@RequestBody PrescriptionDTO dto){
        
        return ResponseEntity.ok(prescriptionService.create(dto));
    }

}
