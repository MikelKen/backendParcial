package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.EnfermeraDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.EnfermeraService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/enfermera")
public class EnfermeraController {
    @Autowired
    private final EnfermeraService enfermeraService;

     @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody EnfermeraDTO request){
        return ResponseEntity.ok(enfermeraService.createEnfermera(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getDoctors(){
        return ResponseEntity.ok(enfermeraService.allEnfermeras());
    }

}
