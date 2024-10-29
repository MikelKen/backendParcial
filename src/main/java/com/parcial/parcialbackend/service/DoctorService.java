package com.parcial.parcialbackend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.DoctorDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.Specialty;
import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.SpecialtyRepository;
import com.parcial.parcialbackend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private SpecialtyRepository specialtyRepository;
    private PasswordEncoder passwordEncoder;

    public ResponseDTO createDoctor(DoctorDTO request){
        try {

            if(userRepository.existsByCi(String.valueOf(request.getCi()))){
                throw new RuntimeException("Ya existe un usuario con este numero de CI");
            }
            
            Users user = Users.builder()
                        .ci(String.valueOf(request.getCi()))//ci
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .address(request.getAddress())
                        .role(Role.MEDICO)
                        .build();
            
            userRepository.save(user);

          Specialty specialty = specialtyRepository.findByName(request.getSpeciality()).orElseThrow(()-> new RuntimeException("Specialty not found"));

          Doctor newDoctor = Doctor.builder()
                            .ci(request.getCi())
                            .user(user)
                            .specialty(specialty)
                            .build(); 
            
            doctorRepository.save(newDoctor);

            return ResponseDTO.builder()
            .data(newDoctor)
            .success(true)
            .error(false)
            .message("Doctor created successful ")
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

    public ResponseDTO allDoctors(){
        try {

           List<Doctor> doctors =  doctorRepository.findAll();

            return ResponseDTO.builder()
            .data(doctors)
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
