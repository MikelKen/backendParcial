package com.parcial.parcialbackend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.DoctorDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.Role;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.Speciality;
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

          Speciality speciality = specialtyRepository.findByName(request.getSpeciality()).orElseThrow(()-> new RuntimeException("Specialty not found"));

          Doctor newDoctor = Doctor.builder()
                            .ci(request.getCi())
                            .user(user)
                            .speciality(speciality)
                            .build(); 
            
            doctorRepository.save(newDoctor);

            return ResponseDTO.builder()
            .data(newDoctor)
            .success(true)
            .error(false)
            .message("Doctor created successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO createdDoctor(DoctorDTO dto){
        try {
            if(doctorRepository.existsByCi(dto.getCi())){
                throw new RuntimeException("Ya existe un doctor con este numero de CI");
            }
            boolean userExit = userRepository.existsByCi(String.valueOf(dto.getCi()));

            Users user ;
            if(userExit == false){
                 user = Users.builder()
                            .ci(String.valueOf(dto.getCi()))
                            .name(dto.getName())
                            .email(dto.getEmail())
                            .password(passwordEncoder.encode(dto.getPassword()))
                            .phone(dto.getPhone())
                            .address(dto.getAddress())
                            .role(Role.MEDICO)
                            .build();
                userRepository.save(user);
            }else {
                user = userRepository.findByCi(String.valueOf(dto.getCi())).orElseThrow(()-> new RuntimeException("User not found"));
            }

            Speciality speciality = specialtyRepository.findByName(dto.getSpeciality()).orElseThrow(()-> new RuntimeException("Specialty not found"));


            Doctor newDoctor = Doctor.builder()
                        .ci(dto.getCi())
                        .user(user)
                        .speciality(speciality)
                        .build(); 

            doctorRepository.save(newDoctor);

            return ResponseDTO.builder()
            .data(newDoctor)
            .success(true)
            .error(false)
            .message("Pacient created successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
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
            throw new RuntimeException(e.getMessage()); 
        }
    }

}
