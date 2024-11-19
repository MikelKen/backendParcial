package com.parcial.parcialbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.parcial.parcialbackend.DTO.PrescriptionDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Prescription;
import com.parcial.parcialbackend.repository.PrescriptionRepository;
import com.parcial.parcialbackend.service.PrescriptionService;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prescription")
public class PrescriptionController {

    private PrescriptionRepository prescriptionRepository;
    
    @Autowired
    private final PrescriptionService prescriptionService;
    @PostMapping("/create") 
    public ResponseEntity<ResponseDTO> create(@RequestBody PrescriptionDTO dto){
        
        return ResponseEntity.ok(prescriptionService.create(dto));
    }

     // Obtener recetas por ID de consulta
    @GetMapping("/get-by-consultation/{consultationId}")
    public ResponseEntity<ResponseDTO> getByConsultationId(@PathVariable Integer consultationId) {
        return ResponseEntity.ok(prescriptionService.getByConsultationId(consultationId));
    }

    
@GetMapping("/get-by-patient/{patientId}")
public ResponseEntity<ResponseDTO> getByPatientId(@PathVariable Integer patientId) {
    List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId);

    if (prescriptions == null || prescriptions.isEmpty()) {
        return ResponseEntity.ok(
            ResponseDTO.builder()
                .data(Collections.emptyList()) // Devuelve un array vacío en lugar de null
                .success(true)
                .error(false)
                .message("No se encontraron recetas para este paciente")
                .build()
        );
    }

    return ResponseEntity.ok(
        ResponseDTO.builder()
            .data(prescriptions)
            .success(true)
            .error(false)
            .message("Recetas encontradas")
            .build()
    );
}


}
