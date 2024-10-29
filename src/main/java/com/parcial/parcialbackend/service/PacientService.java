package com.parcial.parcialbackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.PacientDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.PacientRepository;
import com.parcial.parcialbackend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PacientService {
    private UserRepository userRepository;
    private PacientRepository pacientRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseDTO createPacient(PacientDTO request){
        try {
            if(pacientRepository.existsByCi(request.getCi())){
                throw new RuntimeException("Ya existe un paciente con este numero de CI");
            }
            boolean userExit = userRepository.existsByCi(String.valueOf(request.getCi()));

            Users user ;
            if(userExit == false){
                 user = Users.builder()
                            .ci(String.valueOf(request.getCi()))
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .phone(request.getPhone())
                            .address(request.getAddress())
                            .role(Role.PACIENTE)
                            .build();
                userRepository.save(user);
            }else {
                user = userRepository.findByCi(String.valueOf(request.getCi())).orElseThrow(()-> new RuntimeException("User not found"));
            }

            Pacient pacient = Pacient.builder()
                            .ci(request.getCi())
                            .dateOfBirth(request.getDateOfBirth())
                            .age(request.getAge())
                            .sexo(request.getSexo())
                            .user(user)
                            .build();
            pacientRepository.save(pacient);

            return ResponseDTO.builder()
            .data(pacient)
            .success(true)
            .error(false)
            .message("Pacient created successful ")
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
