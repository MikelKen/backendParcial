package com.parcial.parcialbackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.ConsultationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Consultation;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.MedicalRecord;
import com.parcial.parcialbackend.entity.PreConsultation;
import com.parcial.parcialbackend.repository.ConsultationRepository;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.MedicalRecordRepository;
import com.parcial.parcialbackend.repository.PreConsultationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultationService {
    private ConsultationRepository consultationRepository;
    private DoctorRepository doctorRepository;
    private PreConsultationRepository preConsultationRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public ResponseDTO createConsult(ConsultationDTO dto){
        try {
         
            PreConsultation preConsultation = preConsultationRepository.findById(dto.getPreCosultId()).orElseThrow(()-> new RuntimeException("Pre-Consulta no existe"));

            Doctor doctor = doctorRepository.findByCi(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Medico no encontrado"));

            MedicalRecord medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId()).orElseThrow(()-> new RuntimeException("Historia clinica no encontrada"));

            Consultation consultation = Consultation.builder()
                                    .date(Optional.ofNullable(dto.getDate()).orElse(LocalDate.now()))  
                                    .diagnosis(Optional.ofNullable(dto.getDiagnosis()).orElse("Sin diagnóstico"))  
                                    .state(Optional.ofNullable(dto.getState()).orElse(null))  
                                    .observation(Optional.ofNullable(dto.getObservation()).orElse("Sin observaciones"))  
                                    .doctor(doctor)
                                    .preConsultation(preConsultation)
                                    .medicalRecord(medicalRecord)
                                    .build();
            consultationRepository.save(consultation);
            return ResponseDTO.builder()
            .data(consultation)
            .success(true)
            .error(false)
            .message("Consultation created successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO getConsultsByDoctorCi(Integer id) {
        try {
            
            List<Consultation> consultations = consultationRepository.findByDoctorCi(id);
            if(consultations.isEmpty()) throw new RuntimeException("El Medico no tiene consultas");
            
            return ResponseDTO.builder()
            .data(consultations)
            .success(true)
            .error(false)
            .message("Consultas de un medico")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }        
    }
    public ResponseDTO getConsultsByHistoryId(Integer id) {
        try {
            
            List<Consultation> consultations = consultationRepository.findByMedicalRecordId(id);
            if(consultations.isEmpty())throw new RuntimeException("La historia no tiene consultas"); 
            
            return ResponseDTO.builder()
            .data(consultations)
            .success(true)
            .error(false)
            .message("Consultas de historia clinica")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
          
        }        
    }
}
