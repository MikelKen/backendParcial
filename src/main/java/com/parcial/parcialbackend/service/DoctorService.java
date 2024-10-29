package com.parcial.parcialbackend.service;

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
            Users user = Users.builder()
                        .id(request.getId())//ci
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
                            .id(request.getId())
                            .user(user)
                            .specialty(specialty)
                            .build(); 
            
            doctorRepository.save(newDoctor);

            return ResponseDTO.builder()
            .data(newDoctor)
            .success(false)
            .error(true)
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

}
