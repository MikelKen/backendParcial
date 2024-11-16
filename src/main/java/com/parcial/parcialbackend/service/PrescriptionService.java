package com.parcial.parcialbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.PrescriptionDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Consultation;
import com.parcial.parcialbackend.entity.Prescription;
import com.parcial.parcialbackend.repository.ConsultationRepository;
import com.parcial.parcialbackend.repository.PrescriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrescriptionService {
    
    @Autowired
    private final PrescriptionRepository prescriptionRepository;
    @Autowired ConsultationRepository consultationRepository;
    public ResponseDTO create(PrescriptionDTO dto){
        try {
            Consultation consultation = consultationRepository.findById(dto.getConsultId()).orElseThrow(()-> new RuntimeException("Consulta no encontrada"));

            Prescription prescription = Prescription.builder()
                                .medicine(dto.getMedicine())
                                .dosis(dto.getDosis())
                                .frecuency(dto.getFrecuency())
                                .duration(dto.getDuration())
                                .observation(dto.getObservation())
                                .consulta(consultation)
                                .build();

            prescriptionRepository.save(prescription);

            return ResponseDTO.builder()
            .data(prescription)
            .success(true)
            .error(false)
            .message("Receta medica creada exitosamente")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }
}
