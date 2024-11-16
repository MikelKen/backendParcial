package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.PreConsultationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.PreConsultationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pre-consult")
public class PreConsultController {
    
    @Autowired
    private final PreConsultationService preConsultationService;

    @PostMapping("/create/{id}") //mardar el ci de la enfermera
    public ResponseEntity<ResponseDTO> createId(@RequestBody PreConsultationDTO dto, @PathVariable Integer id){
        
        return ResponseEntity.ok(preConsultationService.createPreConsult(dto, id));
    }

    @PostMapping("/create") //mardar el ci de la enfermera
    public ResponseEntity<ResponseDTO> create(@RequestBody PreConsultationDTO dto, @PathVariable Integer id){
        
        return ResponseEntity.ok(preConsultationService.createPreConsult(dto, id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getPreConsults(){
    
        return ResponseEntity.ok(preConsultationService.getPreConsults());
    }

    @GetMapping("/get-one")//mandar el id de la cita en el body
    public ResponseEntity<ResponseDTO> getHourDoctors(@RequestBody PreConsultationDTO dto){
       
        return ResponseEntity.ok(preConsultationService.getPreConsutByIdCita(dto));
    }
}
