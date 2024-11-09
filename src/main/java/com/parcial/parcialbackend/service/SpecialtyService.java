package com.parcial.parcialbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.SpecialtyDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Speciality;
import com.parcial.parcialbackend.repository.SpecialtyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public ResponseDTO createEspecialidad(SpecialtyDTO request){
        try {
            Speciality nuevo = Speciality.builder()
                            .name(request.getName())
                            .description(request.getDescription())
                            .build();
                specialtyRepository.save(nuevo);
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

    public ResponseDTO allSpecialys(){
        try {

           List<Speciality> specialitys = specialtyRepository.findAll();

            return ResponseDTO.builder()
            .data(specialitys)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }
}
