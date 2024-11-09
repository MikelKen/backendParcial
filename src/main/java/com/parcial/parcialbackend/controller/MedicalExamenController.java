package com.parcial.parcialbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.MedicalExaminationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.MedicalExaminationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/examen")
public class MedicalExamenController {
     private final MedicalExaminationService medicalExaminationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody MedicalExaminationDTO request){
        return ResponseEntity.ok(medicalExaminationService.createExamen(request));
    }

    @GetMapping("/get-examenPacient/{id}") //mandar el ci del paciente para retornar sus examenes
    public ResponseEntity<ResponseDTO> getConsultDoctor(@PathVariable Integer id){
        return ResponseEntity.ok(medicalExaminationService.getExamensByPacinteCi(id));
    }

    @GetMapping("/get-examenConsult/{id}") //mandar el id de la cosulta para retornar los examenes de una consulta
    public ResponseEntity<ResponseDTO> getConsultHistory(@PathVariable Integer id){
        return ResponseEntity.ok(medicalExaminationService.getExamensByConsultId(id));
    }
}
