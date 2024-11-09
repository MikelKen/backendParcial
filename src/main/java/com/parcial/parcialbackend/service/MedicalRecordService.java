package com.parcial.parcialbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.MedicalRecord;
import com.parcial.parcialbackend.repository.MedicalRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicalRecordService {
    private MedicalRecordRepository medicalRecordRepository;

    public ResponseDTO getAll(){
        try {
           List<Map<String, Object>> medicalRecordList = medicalRecordRepository.findAll().stream()
            .map(record -> {
                Map<String, Object> mappedRecord = new HashMap<>();
                mappedRecord.put("id", record.getId());
                mappedRecord.put("creationDate", record.getCreationDate());
                mappedRecord.put("creationTime", record.getCreationTime());
                mappedRecord.put("ci", record.getPacient().getCi());
                mappedRecord.put("dateOfBirth", record.getPacient().getDateOfBirth());
                mappedRecord.put("age", record.getPacient().getAge());
                mappedRecord.put("sexo", record.getPacient().getSexo());
                mappedRecord.put("name", record.getPacient().getUser().getName());
                mappedRecord.put("email", record.getPacient().getUser().getEmail());
                mappedRecord.put("phone", record.getPacient().getUser().getPhone());
                return mappedRecord;
            })
            .collect(Collectors.toList());

            return ResponseDTO.builder()
            .data(medicalRecordList)
            .success(true)
            .error(false)
            .message("Medical get all successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO getAllByIdPacient(){
        try {
            List<Map<String,Object>> medicalRecords = medicalRecordRepository.findAll().stream()
            .map(record -> {
                Map<String, Object> mappedRecord = new HashMap<>();
                mappedRecord.put("id", record.getId());
                mappedRecord.put("creationDate", record.getCreationDate());
                mappedRecord.put("creationTime", record.getCreationTime());
                mappedRecord.put("ci", record.getPacient().getCi());
                mappedRecord.put("namePacient", record.getPacient().getUser().getName());
                mappedRecord.put("dateOfBirth", record.getPacient().getDateOfBirth());
                mappedRecord.put("age", record.getPacient().getAge());
                mappedRecord.put("sexo", record.getPacient().getSexo());
                mappedRecord.put("name", record.getPacient().getUser().getName());
                mappedRecord.put("email", record.getPacient().getUser().getEmail());
                mappedRecord.put("phone", record.getPacient().getUser().getPhone());
                
                return mappedRecord;
            })
            .collect(Collectors.toList());

            return ResponseDTO.builder()
            .data(medicalRecords)
            .success(true)
            .error(false)
            .message("Medical get all successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }
}
