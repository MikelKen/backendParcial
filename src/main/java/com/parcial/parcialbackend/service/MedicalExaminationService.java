package com.parcial.parcialbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.MedicalExaminationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Consultation;
import com.parcial.parcialbackend.entity.MedicalExamination;
import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.repository.ConsultationRepository;
import com.parcial.parcialbackend.repository.MedicalExaminationRepository;
import com.parcial.parcialbackend.repository.PacientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicalExaminationService {
    private MedicalExaminationRepository medicalExaminationRepository;
    private PacientRepository pacientRepository;
    private ConsultationRepository consultationRepository;

    public ResponseDTO createExamen(MedicalExaminationDTO dto){
        try {
            System.out.println("por : ++++++++++++++++++++" + dto);

            Pacient pacient = pacientRepository.findByCi(dto.getPacientId()).orElseThrow(()-> new RuntimeException("Paciente no existe"));

            Consultation consultation = consultationRepository.findById(dto.getConsultId()).orElseThrow(()-> new RuntimeException("Consulta no encontrada"));

           MedicalExamination medicalExamination = MedicalExamination.builder()
                                .date(dto.getDate())
                                .type(dto.getType())
                                .result(dto.getResult())
                                .pacient(pacient)
                                .consultation(consultation)
                                .build();
            medicalExaminationRepository.save(medicalExamination);
            return ResponseDTO.builder()
            .data(medicalExamination)
            .success(true)
            .error(false)
            .message("Consultation created successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO getExamensByPacinteCi(Integer id) {
        try {
            
            List<MedicalExamination> medicalExaminations  = medicalExaminationRepository.findByPacient_Ci(id);
            if(medicalExaminations.isEmpty()) throw new RuntimeException("El Paciente no tiene examenes");
            

              List<Map<String, Object>> mappedExams = medicalExaminations.stream().map(exam -> {
            Map<String, Object> examData = new HashMap<>();
            examData.put("id", exam.getId());
            examData.put("date", exam.getDate());
            examData.put("type", exam.getType());
            examData.put("result", exam.getResult());
           
            Map<String, Object> pacientData = new HashMap<>();
            pacientData.put("ci", exam.getPacient().getCi());
            pacientData.put("name", exam.getPacient().getUser().getName());                      
            examData.put("pacient", pacientData);

            return examData;
        }).collect(Collectors.toList());

            return ResponseDTO.builder()
            .data(mappedExams)
            .success(true)
            .error(false)
            .message("Consultas de un medico")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }        
    }
    public ResponseDTO getExamensByConsultId(Integer id) {
        try {
            
            List<MedicalExamination> medicalExaminations = medicalExaminationRepository.findByConsultation_Id(id);
            if(medicalExaminations.isEmpty())throw new RuntimeException("La historia no tiene consultas"); 
            
            List<Map<String, Object>> mappedExams = medicalExaminations.stream().map(exam -> {
                Map<String, Object> examData = new HashMap<>();
                examData.put("id", exam.getId());
                examData.put("date", exam.getDate());
                examData.put("type", exam.getType());
                examData.put("result", exam.getResult());
               
                Map<String, Object> pacientData = new HashMap<>();
                pacientData.put("ci", exam.getPacient().getCi());
                pacientData.put("name", exam.getPacient().getUser().getName());                      
                examData.put("pacient", pacientData);

                Map<String,Object> consultData = new HashMap<>();
                consultData.put("date",exam.getConsultation().getDate());
                consultData.put("diagnosis",exam.getConsultation().getDiagnosis());
                consultData.put("observation",exam.getConsultation().getObservation());
                examData.put("consult",consultData);
    
                return examData;
            }).collect(Collectors.toList());

            return ResponseDTO.builder()
            .data(mappedExams)
            .success(true)
            .error(false)
            .message("Consultas de historia clinica")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
          
        }        
    }
}
