package com.parcial.parcialbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.OpeningHourDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.OpeningHourService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/opening-hour")//
public class OpeningHourController {
    @Autowired
    private final OpeningHourService openingHourService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody OpeningHourDTO dto){
        
        return ResponseEntity.ok(openingHourService.createOpeningHour(dto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getAllOpeningHours() {
        return ResponseEntity.ok(openingHourService.getAllOpeningHours());
    }

}
