package com.parcial.parcialbackend.service;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.SpecialtyDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Specialty;
import com.parcial.parcialbackend.repository.SpecialtyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialtyService {
    private final SpecialtyRepository especialidadRepository;

    public ResponseDTO createEspecialidad(SpecialtyDTO request){
        try {
            Specialty nuevo = Specialty.builder()
                            .name(request.getNombre())
                            .description(request.getDescripcion())
                            .build();
            especialidadRepository.save(nuevo);
        return ResponseDTO.builder()
            .data(nuevo)
            .success(true)
            .error(false)
            .message("Especialidad creada exitosamente")
            .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
            .data(null)
            .success(false)
            .error(true)
            .message(e.getMessage())
            .build();
        }
    }

}
