package com.parcial.parcialbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.ConsultationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.ConsultationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/consult")
public class ConsultatioController {
    private final ConsultationService consultationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody ConsultationDTO request){
        return ResponseEntity.ok(consultationService.createConsult(request));
    }

    @GetMapping("/get-consultDoctor/{id}") //mandar el ci del doctor para retornar sus consultas
    public ResponseEntity<ResponseDTO> getConsultDoctor(@PathVariable Integer id){
        return ResponseEntity.ok(consultationService.getConsultsByDoctorCi(id));
    }

    @GetMapping("/get-consultHistori/{id}") //mandar el id del historial para retornar sus consultas
    public ResponseEntity<ResponseDTO> getConsultHistory(@PathVariable Integer id){
        return ResponseEntity.ok(consultationService.getConsultsByHistoryId(id));
    }

}
