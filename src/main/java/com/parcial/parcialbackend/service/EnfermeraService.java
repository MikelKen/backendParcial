package com.parcial.parcialbackend.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.parcial.parcialbackend.DTO.EnfermeraDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Enfermera;

import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.EnfermeraRepository;
import com.parcial.parcialbackend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnfermeraService {
    private UserRepository userRepository;
    private EnfermeraRepository enfermeraRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseDTO createEnfermera(EnfermeraDTO dto){
        try {
            if (enfermeraRepository.existsByCi((dto.getCi()))) {
                throw new RuntimeException("Ya existe un enfermer@ con este numero de CI");
            }
            boolean userExit = userRepository.existsByCi(String.valueOf(dto.getCi()));

            Users user;
            
            if(userExit == false){
                 user = Users.builder()
                            .ci(String.valueOf(dto.getCi()))
                            .name(dto.getName())
                            .email(dto.getEmail())
                            .password(passwordEncoder.encode(dto.getPassword()))
                            .phone(dto.getPhone())
                            .address(dto.getAddress())
                            .role(Role.ENFERMERA)
                            .build();
                userRepository.save(user);
            }else {
                user = userRepository.findByCi(String.valueOf(dto.getCi())).orElseThrow(()-> new RuntimeException("User not found"));
            }

            Enfermera enfermera = Enfermera.builder()
                                .ci(dto.getCi())
                                .licence_number(dto.getNumberLicense())
                                .user(user)
                                .build();
            enfermeraRepository.save(enfermera);

            return ResponseDTO.builder()
            .data(enfermera)
            .success(true)
            .error(false)
            .message("Enfermer@ creado ")
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

     public ResponseDTO allEnfermeras(){
        try {


           List<Enfermera> enfermeras = enfermeraRepository.findAll();

            return ResponseDTO.builder()
            .data(enfermeras)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
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
