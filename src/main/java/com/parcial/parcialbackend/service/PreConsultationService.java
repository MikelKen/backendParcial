package com.parcial.parcialbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.PreConsultationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Enfermera;
import com.parcial.parcialbackend.entity.PreConsultation;
import com.parcial.parcialbackend.entity.TimeBlock;
import com.parcial.parcialbackend.repository.EnfermeraRepository;
import com.parcial.parcialbackend.repository.PreConsultationRepository;
import com.parcial.parcialbackend.repository.TimeBlockRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PreConsultationService {
    @Autowired
    private final PreConsultationRepository preConsultationRepository;
    @Autowired
    private final EnfermeraRepository enfermeraRepository;
    @Autowired
    private final TimeBlockRepository timeBlockRepository;

    public ResponseDTO createPreConsult(PreConsultationDTO dto, Integer id){
        try {

            Enfermera enfermera = enfermeraRepository.findByCi(id).orElseThrow(()-> new RuntimeException("Enfermera no encontrada"));

            TimeBlock cita = timeBlockRepository.findById(dto.getCitaId()).orElseThrow(()-> new RuntimeException("Appoinment not fond"));

            PreConsultation newPreConsult = PreConsultation.builder()
            .arterialPressure(dto.getArterialPressure())
            .heartRate(dto.getHeartRate())
            .weight(dto.getWeight())
            .temperature(dto.getTemperature())
            .date(dto.getDate())
            .enfermera(enfermera)
            .cita(cita)
            .build();

            preConsultationRepository.save(newPreConsult);
            return ResponseDTO.builder()
            .data(newPreConsult)
            .success(true)
            .error(false)
            .message("Pre consulta creada exitosamente")
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

    public ResponseDTO getPreConsults(){
        try {
            List<PreConsultation> preConsultList  = preConsultationRepository.findAll();

             List<Map<String, Object>> preConsultMaps = preConsultList.stream().map(preConsult -> {
            Map<String, Object> preConsultMap = new HashMap<>();
            preConsultMap.put("id", preConsult.getId());
            preConsultMap.put("arterialPressure", preConsult.getArterialPressure());
            preConsultMap.put("heartRate", preConsult.getHeartRate());
            preConsultMap.put("temperature", preConsult.getTemperature());
            preConsultMap.put("weight", preConsult.getWeight());
            preConsultMap.put("date", preConsult.getDate());
        
            preConsultMap.put("enfermeraName", preConsult.getEnfermera().getUser().getName());

            preConsultMap.put("doctorName", preConsult.getCita().getOpeningHour().getDoctor().getUser().getName());
            preConsultMap.put("specialityName", preConsult.getCita().getOpeningHour().getDoctor().getSpeciality().getName());
 
            preConsultMap.put("patientName", preConsult.getCita().getPacient().getUser().getName());
            preConsultMap.put("patientAge", preConsult.getCita().getPacient().getAge());
            preConsultMap.put("patientSex", preConsult.getCita().getPacient().getSexo());

            preConsultMap.put("citaDate", preConsult.getCita().getDate());
            preConsultMap.put("citaState", preConsult.getCita().getState());
            
            return preConsultMap;
        }).collect(Collectors.toList());
            
            return ResponseDTO.builder()
            .data(preConsultMaps)
            .success(false)
            .error(true)
            .message(" ")
            .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
            .data(null)
            .success(false)
            .error(true)
            .message("Error "+e)
            .build();
        }
    }

    public ResponseDTO getPreConsutByIdCita(PreConsultationDTO dto){
        try {
            PreConsultation preConsult  = preConsultationRepository.findById(dto.getCitaId()).orElseThrow(()-> new RuntimeException("Pre-Consult not found"));

        Map<String, Object> preConsultMap = new HashMap<>();
        preConsultMap.put("id", preConsult.getId());
        preConsultMap.put("arterialPressure", preConsult.getArterialPressure());
        preConsultMap.put("heartRate", preConsult.getHeartRate());
        preConsultMap.put("temperature", preConsult.getTemperature());
        preConsultMap.put("weight", preConsult.getWeight());
        preConsultMap.put("date", preConsult.getDate());
        preConsultMap.put("enfermeraName", preConsult.getEnfermera().getUser().getName());
        
        preConsultMap.put("doctorName", preConsult.getCita().getOpeningHour().getDoctor().getUser().getName());
        preConsultMap.put("specialityName", preConsult.getCita().getOpeningHour().getDoctor().getSpeciality().getName());
       
        preConsultMap.put("patientName", preConsult.getCita().getPacient().getUser().getName());
        preConsultMap.put("patientAge", preConsult.getCita().getPacient().getAge());
        preConsultMap.put("patientSex", preConsult.getCita().getPacient().getSexo());
        
        preConsultMap.put("citaDate", preConsult.getCita().getDate());
        preConsultMap.put("citaState", preConsult.getCita().getState());
        

            return ResponseDTO.builder()
            .data(preConsultMap)
            .success(false)
            .error(true)
            .message(" ")
            .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
            .data(null)
            .success(false)
            .error(true)
            .message("Error "+e)
            .build();
        }
    }

}
