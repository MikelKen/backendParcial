package com.parcial.parcialbackend.service;


import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.DTO.TimeBlockDTO;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.OpeningHour;
import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.entity.TimeBlock;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.OpeningHourRepository;
import com.parcial.parcialbackend.repository.PacientRepository;
import com.parcial.parcialbackend.repository.TimeBlockRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimeBlockService {
    private final TimeBlockRepository timeBlockRepository;
    private final DoctorRepository doctorRepository;
    private final OpeningHourRepository openingHourRepository;
    private final PacientRepository pacientRepository;


    public ResponseDTO createSheet(TimeBlockDTO dto){
        try {
       
            var doctor = doctorRepository.findByCi(dto.getDoctorCi());
      
            if(doctor == null){
                throw new RuntimeException("Doctor not found");
            }
     
            LocalDate date = dto.getDate();

            String dayWeek = removeAccents(date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es","ES")).toUpperCase());
            
         
            if(timeBlockRepository.existsByDateAndOpeningHourDoctorCi(date, dto.getDoctorCi())){
                throw new RuntimeException("El doctor ya tiene fichas creadas para esta fecha");
            }
            
            List<OpeningHour> openingHours = openingHourRepository.findByDoctorCiAndDayOfWeek(dto.getDoctorCi(), dayWeek);
           
            if(openingHours.isEmpty()){
               
                throw new RuntimeException("the doctor does not attend that day");
            }
            List<TimeBlock> timeBlocks = new ArrayList<>();


            for (OpeningHour openingHour : openingHours ){
                LocalTime startTime = openingHour.getStartTime();
                LocalTime endTime = openingHour.getEndTime();

                while (startTime.isBefore(endTime)){
                    LocalTime blockEndTime = startTime.plusHours(1);
                    if(blockEndTime.isAfter(endTime)){
                        blockEndTime = endTime;
                    }
                    TimeBlock timeBlock = TimeBlock.builder()
                                        .date(date)
                                        .startTime(startTime)
                                        .endTime(blockEndTime)
                                        .state("DISPONIBLE")
                                        .openingHour(openingHour)
                                        .build();
                    timeBlocks.add(timeBlock);
                    startTime = blockEndTime;
                }
            }

            timeBlockRepository.saveAll(timeBlocks);

            return ResponseDTO.builder()
            .data(timeBlocks)
            .success(true)
            .error(false)
            .message("Ficha medicas")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public String removeAccents(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}","");
    }

    public ResponseDTO getAviableFich(Integer doctorCi){
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findByCi(doctorCi);
            Doctor doctor = optionalDoctor.orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

            List<TimeBlock> fichas = timeBlockRepository.findAllFichasByDoctorCi(doctorCi);

            if(fichas.isEmpty()){
                throw new RuntimeException("El medico no tiene fichas creadas");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("doctorCi", doctorCi);
            response.put("name", doctor.getUser().getName());

            List<Map<String, Object>> fichasList = fichas.stream().map(ficha -> {
                Map<String,Object> fichaMap = new HashMap<>();
                fichaMap.put("id",ficha.getId());
                fichaMap.put("date",ficha.getDate().toString());
                fichaMap.put("day", ficha.getOpeningHour().getDayWeek());
                fichaMap.put("startTime", ficha.getStartTime().toString());
                fichaMap.put("endTime", ficha.getEndTime().toString());
                fichaMap.put("state", ficha.getState());
                return fichaMap;
            }).collect(Collectors.toList());

            response.put("fichas", fichasList);


            return ResponseDTO.builder()
            .data(response)
            .success(true)
            .error(false)
            .message("Ficha medicas obtenidas")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO getFichDispon(Integer doctorCi){
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findByCi(doctorCi);
            Doctor doctor = optionalDoctor.orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

            List<TimeBlock> fichas = timeBlockRepository.findAvailableFichasByDoctorCi(doctorCi);

            if(fichas.isEmpty()){
                throw new RuntimeException("El medico no tiene fichas disponibles");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("doctorCi", doctorCi);
            response.put("name", doctor.getUser().getName());

            List<Map<String, Object>> fichasList = fichas.stream().map(ficha -> {
                Map<String,Object> fichaMap = new HashMap<>();
                fichaMap.put("id", ficha.getId());
                fichaMap.put("date",ficha.getDate().toString());
                fichaMap.put("day", ficha.getOpeningHour().getDayWeek());
                fichaMap.put("startTime", ficha.getStartTime().toString());
                fichaMap.put("endTime", ficha.getEndTime().toString());
                fichaMap.put("state", ficha.getState());
                return fichaMap;
            }).collect(Collectors.toList());

            response.put("fichas", fichasList);


            return ResponseDTO.builder()
            .data(response)
            .success(true)
            .error(false)
            .message("Ficha medicas obtenidas")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }

    public ResponseDTO reserveFich(TimeBlockDTO dto){
        try {

            Optional<TimeBlock> optionalTimeBlock = timeBlockRepository.findById(dto.getFichaId());
            TimeBlock timeBlock = optionalTimeBlock.orElseThrow(() -> new RuntimeException("Ficha no encontrada"));

            if (!"DISPONIBLE".equals(timeBlock.getState())) {
                throw new RuntimeException("La ficha no disponible");
            }

            Optional<Pacient> optionalPacient = pacientRepository.findByCi(dto.getPacientCi());
            Pacient pacient = optionalPacient.orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

            LocalDate dateToReserve = timeBlock.getDate();
            List<TimeBlock> reservedFichas = timeBlockRepository.findReservedFichasByPacientAndDate(pacient.getId(), dateToReserve);

            if (!reservedFichas.isEmpty()) {
                throw new RuntimeException("El paciente ya tiene una ficha reservada para este día.");
            }
       
            timeBlock.setState("RESERVADO");
            timeBlock.setPacient(pacient); 
            timeBlockRepository.save(timeBlock);


            return ResponseDTO.builder()
            .data(timeBlock)
            .success(true)
            .error(false)
            .message("Ficha reservada exitosa")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }
}
