package com.parcial.parcialbackend.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.PacientDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.auth.Role;

import com.parcial.parcialbackend.entity.MedicalRecord;
import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.entity.Users;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.MedicalRecordRepository;
import com.parcial.parcialbackend.repository.PacientRepository;
import com.parcial.parcialbackend.repository.TimeBlockRepository;
import com.parcial.parcialbackend.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PacientService {
    
    private UserRepository userRepository;
    private PacientRepository pacientRepository;
    private PasswordEncoder passwordEncoder;
    private MedicalRecordRepository medicalRecordRepository;
    private DoctorRepository doctorRepository;
    private TimeBlockRepository timeBlockRepository;

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

            MedicalRecord medicalRecord = MedicalRecord.builder()
                                        .pacient(pacient)
                                        .build();
            medicalRecordRepository.save(medicalRecord);
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

    public ResponseDTO allPacients(){
        try {


           List<Pacient> pacients = pacientRepository.findAll();

            return ResponseDTO.builder()
            .data(pacients)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }


    public ResponseDTO getPacientID(HttpServletRequest request){
      
        try {

           String pacientId = (String) request.getAttribute("userId");
           
            Pacient pacient = pacientRepository.findByCi(Integer.parseInt(pacientId)).orElse(null);

            Map<String, Object> pacientData = new HashMap<>();
            pacientData.put("id", pacient.getId());
            pacientData.put("ci", pacient.getCi());
            pacientData.put("dateOfBirth", pacient.getDateOfBirth());
            pacientData.put("age", pacient.getAge());
            pacientData.put("sexo", pacient.getSexo());
            pacientData.put("name", pacient.getUser().getName());
            pacientData.put("phone", pacient.getUser().getPhone());
            pacientData.put("address", pacient.getUser().getAddress());
            

            return ResponseDTO.builder()
            .data(pacientData)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO getPacientIdMovil(HttpServletRequest request){
      
        try {

           String pacientId = (String) request.getAttribute("userId");
           
            Pacient pacient = pacientRepository.findByCi(Integer.parseInt(pacientId)).orElse(null);

            Map<String, Object> pacientData = new HashMap<>();
            pacientData.put("id", pacient.getId());
            pacientData.put("ci", pacient.getCi());
            pacientData.put("dateOfBirth", pacient.getDateOfBirth());
            pacientData.put("age", pacient.getAge());
            pacientData.put("sexo", pacient.getSexo());
            pacientData.put("name", pacient.getUser().getName());
            pacientData.put("phone", pacient.getUser().getPhone());
            pacientData.put("address", pacient.getUser().getAddress());
            
            List<Map<String,Object>> doctors =  doctorRepository.findAll().stream().map(record -> {
                Map<String,Object> doctorData = new HashMap<>();
                doctorData.put("id", record.getId());
                doctorData.put("ci",record.getCi());
                doctorData.put("nameDoctor",record.getUser().getName());
                doctorData.put("email",record.getUser().getEmail());
                doctorData.put("phone",record.getUser().getPhone());
                doctorData.put("address",record.getUser().getAddress());
                doctorData.put("speciality",record.getSpeciality().getName());
                doctorData.put("description",record.getSpeciality().getDescription());


                return doctorData;
            })
            .collect(Collectors.toList()); 

            System.out.println("-------------------"+LocalDate.now());
            List<Map<String,Object>> appoinments = timeBlockRepository.findByPacientCiAndDate(Integer.parseInt(pacientId),LocalDate.now()).stream().map(record ->{
                Map<String, Object> appoinmetData = new HashMap<>();
                appoinmetData.put("id", record.getId());
                appoinmetData.put("date", record.getDate().toString());
                appoinmetData.put("starTime", record.getStartTime().toString());
                appoinmetData.put("day",record.getOpeningHour().getDayWeek());
                appoinmetData.put("status", record.getState());
                appoinmetData.put("nameDoctor", record.getOpeningHour().getDoctor().getUser().getName());
                appoinmetData.put("speciality", record.getOpeningHour().getDoctor().getSpeciality().getName());

                return appoinmetData;
            }).collect(Collectors.toList());

            pacientData.put("doctor", doctors);
            pacientData.put("appointment", appoinments);
            System.out.println(pacientData);
            return ResponseDTO.builder()
            .data(pacientData)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }


}
