package com.parcial.parcialbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.parcialbackend.DTO.NotificationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

     @PostMapping("/create")// mandar el token del medico 
    public ResponseEntity<ResponseDTO> create(@RequestBody NotificationDTO dto, HttpServletRequest request){
        return ResponseEntity.ok(notificationService.create(dto, request));
    }

    @GetMapping("/get-notifPacient")//mandar el token del paciente para que retorne todas sus notificaciones
    public ResponseEntity<ResponseDTO> getDoctors(HttpServletRequest request){
        return ResponseEntity.ok(notificationService.getNotificationByPacient(request));
    }
}
