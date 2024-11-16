package com.parcial.parcialbackend.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.ConsultationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Consultation;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.MedicalExamination;
import com.parcial.parcialbackend.entity.MedicalRecord;
import com.parcial.parcialbackend.entity.PreConsultation;
import com.parcial.parcialbackend.entity.Prescription;
import com.parcial.parcialbackend.repository.ConsultationRepository;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.MedicalExaminationRepository;
import com.parcial.parcialbackend.repository.MedicalRecordRepository;
import com.parcial.parcialbackend.repository.PreConsultationRepository;
import com.parcial.parcialbackend.repository.PrescriptionRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultationService {
    private ConsultationRepository consultationRepository;
    private DoctorRepository doctorRepository;
    private PreConsultationRepository preConsultationRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private PrescriptionRepository prescriptionRepository;
    private MedicalExaminationRepository medicalExaminationRepository;

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

    public ResponseDTO getConsultsByHistoryUser1(HttpServletRequest request) {
        try {
            String pacientId = (String)request.getAttribute("userId");
            System.out.println(pacientId);
            List<Consultation> consultations = consultationRepository.findConsultationsByPacientCi(Integer.valueOf(pacientId));
            
            
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



    public ResponseDTO getConsultsByHistoryUser(HttpServletRequest request) {
    try {
        String pacientId = (String)request.getAttribute("userId");
        System.out.println(pacientId);
        
        // Obtener las consultas desde el repositorio
        List<Consultation> consultations = consultationRepository.findConsultationsByPacientCi(Integer.valueOf(pacientId));

        // Transformar las consultas a la estructura esperada
        List<Map<String, Object>> responseData = new ArrayList<>();
        
        for (Consultation consultation : consultations) {
            Map<String, Object> consultationData = new HashMap<>();
            
            // Fecha de la consulta (año, mes, día)
            consultationData.put("date", consultation.getDate().toString()); 
            consultationData.put("id", consultation.getId());
            consultationData.put("diagnosis", consultation.getDiagnosis());
            consultationData.put("state", consultation.getState());
            consultationData.put("observation", consultation.getObservation());


            // Información del doctor
            Map<String, Object> doctorData = new HashMap<>();
            doctorData.put("id", consultation.getDoctor().getId());
            doctorData.put("ci", consultation.getDoctor().getCi());
            doctorData.put("name", consultation.getDoctor().getUser().getName());
            doctorData.put("speciality", consultation.getDoctor().getSpeciality().getName());
            consultationData.put("doctor", doctorData);

            // Preconsulta
            Map<String, Object> preConsultationData = new HashMap<>();
            preConsultationData.put("id", consultation.getPreConsultation().getId());
            preConsultationData.put("arterialPressure", consultation.getPreConsultation().getArterialPressure());
            preConsultationData.put("heartRate", consultation.getPreConsultation().getHeartRate());
            preConsultationData.put("temperature", consultation.getPreConsultation().getTemperature());
            preConsultationData.put("weight", consultation.getPreConsultation().getWeight());
            
            // Fecha de la preconsulta (año, mes, día)
            preConsultationData.put("date",consultation.getPreConsultation().getDate().toString() );
            preConsultationData.put("pacientId", consultation.getPreConsultation().getPacientId());
            preConsultationData.put("day", consultation.getPreConsultation().getCita().getOpeningHour().getDayWeek());
            preConsultationData.put("hour", consultation.getPreConsultation().getCita().getStartTime().toString());


            // Información de la enfermera
            Map<String, Object> enfermeraData = new HashMap<>();
            enfermeraData.put("name", consultation.getPreConsultation().getEnfermera().getUser().getName());
            preConsultationData.put("enfermera", enfermeraData);
            
            consultationData.put("preConsultation", preConsultationData);

            // Historial médico
            Map<String, Object> medicalRecordData = new HashMap<>();
            medicalRecordData.put("id", consultation.getMedicalRecord().getId());
            
            // Fecha de creación del expediente médico
            medicalRecordData.put("creationDate", consultation.getMedicalRecord().getCreationDate().toString());
            
            // Hora de creación
            medicalRecordData.put("creationTime", consultation.getMedicalRecord().getCreationTime().toString());
            
            // Información del paciente
            Map<String, Object> pacientData = new HashMap<>();
            pacientData.put("ci", consultation.getMedicalRecord().getPacient().getCi());
            pacientData.put("dateOfBirth", consultation.getMedicalRecord().getPacient().getDateOfBirth().toString());
            pacientData.put("age", consultation.getMedicalRecord().getPacient().getAge());
            pacientData.put("sexo", consultation.getMedicalRecord().getPacient().getSexo());
            pacientData.put("name", consultation.getMedicalRecord().getPacient().getUser().getName());
            medicalRecordData.put("pacient", pacientData);

            consultationData.put("medicalRecord", medicalRecordData);

            //Informacion de la receta medica
            List<Prescription> prescriptions = prescriptionRepository.findByConsultationId(consultation.getId());
            List<Map<String, Object>> prescriptionsData = prescriptions.stream()
                        .map(prescription -> {
                             Map<String, Object> map = new HashMap<>();
                            map.put("id", prescription.getId());
                            map.put("medicine", prescription.getMedicine());
                            map.put("dosis", prescription.getDosis());
                            map.put("frecuency", prescription.getFrecuency());
                            map.put("duration", prescription.getDuration());
                            map.put("observation", prescription.getObservation());
                        return map;
                    })
                    .collect(Collectors.toList());
            consultationData.put("prescriptions", prescriptionsData);
                
            //Informacion de exmenes medicos
            List<MedicalExamination> examMedicos = medicalExaminationRepository.findByConsultation_Id(consultation.getId());
            List<Map<String, Object>> examMedicoData = examMedicos.stream()
                        .map(examMedico -> {
                             Map<String, Object> map = new HashMap<>();
                            map.put("id", examMedico.getId());
                            map.put("type", examMedico.getType());
                            map.put("result", examMedico.getResult());
                            map.put("image", examMedico.getImage());
                          
                        return map;
                    })
                    .collect(Collectors.toList());
            
                    consultationData.put("examMedico", examMedicoData);        
            

            // Añadir la consulta transformada al listado de respuestas
            responseData.add(consultationData);
        }

        // Retornar la respuesta DTO con los datos formateados
        return ResponseDTO.builder()
            .data(responseData)
            .success(true)
            .error(false)
            .message("Consultas de historia clínica")
            .build();
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}

    
public ResponseDTO getConsultsByHistoryDoctor(Integer pacientId) {
    try {
      //  String pacientId = (String)request.getAttribute("userId");
        //System.out.println(pacientId);
        
        // Obtener las consultas desde el repositorio
        List<Consultation> consultations = consultationRepository.findConsultationsByPacientCi(Integer.valueOf(pacientId));

        // Transformar las consultas a la estructura esperada
        List<Map<String, Object>> responseData = new ArrayList<>();
        
        for (Consultation consultation : consultations) {
            Map<String, Object> consultationData = new HashMap<>();
            
            // Fecha de la consulta (año, mes, día)
            consultationData.put("date", consultation.getDate().toString()); 
            consultationData.put("id", consultation.getId());
            consultationData.put("diagnosis", consultation.getDiagnosis());
            consultationData.put("state", consultation.getState());
            consultationData.put("observation", consultation.getObservation());


            // Información del doctor
            Map<String, Object> doctorData = new HashMap<>();
            doctorData.put("id", consultation.getDoctor().getId());
            doctorData.put("ci", consultation.getDoctor().getCi());
            doctorData.put("name", consultation.getDoctor().getUser().getName());
            doctorData.put("speciality", consultation.getDoctor().getSpeciality().getName());
            consultationData.put("doctor", doctorData);

            // Preconsulta
            Map<String, Object> preConsultationData = new HashMap<>();
            preConsultationData.put("id", consultation.getPreConsultation().getId());
            preConsultationData.put("arterialPressure", consultation.getPreConsultation().getArterialPressure());
            preConsultationData.put("heartRate", consultation.getPreConsultation().getHeartRate());
            preConsultationData.put("temperature", consultation.getPreConsultation().getTemperature());
            preConsultationData.put("weight", consultation.getPreConsultation().getWeight());
            
            // Fecha de la preconsulta (año, mes, día)
            preConsultationData.put("date",consultation.getPreConsultation().getDate().toString() );
            preConsultationData.put("pacientId", consultation.getPreConsultation().getPacientId());
            preConsultationData.put("day", consultation.getPreConsultation().getCita().getOpeningHour().getDayWeek());
            preConsultationData.put("hour", consultation.getPreConsultation().getCita().getStartTime().toString());


            // Información de la enfermera
            Map<String, Object> enfermeraData = new HashMap<>();
            enfermeraData.put("name", consultation.getPreConsultation().getEnfermera().getUser().getName());
            preConsultationData.put("enfermera", enfermeraData);
            
            consultationData.put("preConsultation", preConsultationData);

            // Historial médico
            Map<String, Object> medicalRecordData = new HashMap<>();
            medicalRecordData.put("id", consultation.getMedicalRecord().getId());
            
            // Fecha de creación del expediente médico
            medicalRecordData.put("creationDate", consultation.getMedicalRecord().getCreationDate().toString());
            
            // Hora de creación
            medicalRecordData.put("creationTime", consultation.getMedicalRecord().getCreationTime().toString());
            
            // Información del paciente
            Map<String, Object> pacientData = new HashMap<>();
            pacientData.put("ci", consultation.getMedicalRecord().getPacient().getCi());
            pacientData.put("dateOfBirth", consultation.getMedicalRecord().getPacient().getDateOfBirth().toString());
            pacientData.put("age", consultation.getMedicalRecord().getPacient().getAge());
            pacientData.put("sexo", consultation.getMedicalRecord().getPacient().getSexo());
            pacientData.put("name", consultation.getMedicalRecord().getPacient().getUser().getName());
            medicalRecordData.put("pacient", pacientData);

            consultationData.put("medicalRecord", medicalRecordData);

            //Informacion de la receta medica
            List<Prescription> prescriptions = prescriptionRepository.findByConsultationId(consultation.getId());
            List<Map<String, Object>> prescriptionsData = prescriptions.stream()
                        .map(prescription -> {
                             Map<String, Object> map = new HashMap<>();
                            map.put("id", prescription.getId());
                            map.put("medicine", prescription.getMedicine());
                            map.put("dosis", prescription.getDosis());
                            map.put("frecuency", prescription.getFrecuency());
                            map.put("duration", prescription.getDuration());
                            map.put("observation", prescription.getObservation());
                        return map;
                    })
                    .collect(Collectors.toList());
            consultationData.put("prescriptions", prescriptionsData);
                
            //Informacion de exmenes medicos
            List<MedicalExamination> examMedicos = medicalExaminationRepository.findByConsultation_Id(consultation.getId());
            List<Map<String, Object>> examMedicoData = examMedicos.stream()
                        .map(examMedico -> {
                             Map<String, Object> map = new HashMap<>();
                            map.put("id", examMedico.getId());
                            map.put("type", examMedico.getType());
                            map.put("result", examMedico.getResult());
                            map.put("image", examMedico.getImage());
                          
                        return map;
                    })
                    .collect(Collectors.toList());
            
                    consultationData.put("examMedico", examMedicoData);        
            

            // Añadir la consulta transformada al listado de respuestas
            responseData.add(consultationData);
        }

        // Retornar la respuesta DTO con los datos formateados
        return ResponseDTO.builder()
            .data(responseData)
            .success(true)
            .error(false)
            .message("Consultas de historia clínica")
            .build();
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}

    
}
