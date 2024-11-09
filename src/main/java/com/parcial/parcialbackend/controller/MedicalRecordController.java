package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.MedicalRecordService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/medical-record")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/get-allhistories") //retorna todas las historias clinicas
    public ResponseEntity<ResponseDTO> getAllHistories(){
        return ResponseEntity.ok(medicalRecordService.getAll());
    }

    @GetMapping("/get-allhistorie/{id}") //mandar el ci del paciente,retornala historia clinica de un paciente
    public ResponseEntity<ResponseDTO> getFichDisponDoctor(@PathVariable Integer id){
        return ResponseEntity.ok(medicalRecordService.getAllByIdPacient());
    }

}
