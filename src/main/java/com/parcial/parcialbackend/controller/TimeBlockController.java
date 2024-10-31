package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.DTO.TimeBlockDTO;
import com.parcial.parcialbackend.service.TimeBlockService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/time-block")
public class TimeBlockController {
    @Autowired final TimeBlockService timeBlockService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody TimeBlockDTO dto){
        return ResponseEntity.ok(timeBlockService.createSheet(dto));
    }

    @GetMapping("/get-allDoctor/{id}") //retorna todas las fichas de un medico mandar el ci
    public ResponseEntity<ResponseDTO> getAllFichDoctor(@PathVariable Integer id){
        return ResponseEntity.ok(timeBlockService.getAviableFich(id));
    }

    @GetMapping("/get-allDispon/{id}") //retorna todas las fichas de un medico mandar el ci
    public ResponseEntity<ResponseDTO> getFichDisponDoctor(@PathVariable Integer id){
        return ResponseEntity.ok(timeBlockService.getFichDispon(id));
    }

    @PutMapping("/reserve-ficha") //mandar en el body el id de la ficha y el ci del paciente
    public ResponseEntity<ResponseDTO> reseveFicha(@RequestBody TimeBlockDTO dto){
        return ResponseEntity.ok(timeBlockService.reserveFich(dto));
    }
}
