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

import jakarta.servlet.http.HttpServletRequest;
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
                fichaMap.put("turn", ficha.getOpeningHour().getTurn());
                fichaMap.put("startTime", ficha.getStartTime().toString());
                fichaMap.put("endTime", ficha.getEndTime().toString());
                fichaMap.put("state", ficha.getState());
                return fichaMap;
            }).collect(Collectors.toList());

            response.put("fichas", fichasList);

            System.out.println(response);
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


    public ResponseDTO pruebamovil(TimeBlockDTO dto,HttpServletRequest request){
        try {
           String pacientId = (String)request.getAttribute("userId");
    
           Optional<TimeBlock> optionalTimeBlock = timeBlockRepository.findById(dto.getFichaId());
           TimeBlock timeBlock = optionalTimeBlock.orElseThrow(() -> new RuntimeException("Ficha no encontrada"));

           if (!"DISPONIBLE".equals(timeBlock.getState())) {
              throw new RuntimeException("La ficha no disponible");
          
           }
System.out.println(" ----------------------------------");
           Optional<Pacient> optionalPacient = pacientRepository.findByCi(Integer.valueOf(pacientId));
           Pacient pacient = optionalPacient.orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

           LocalDate dateToReserve = timeBlock.getDate();
           List<TimeBlock> reservedFichas = timeBlockRepository.findReservedFichasByPacientAndDate(pacient.getId(), dateToReserve);

           if (!reservedFichas.isEmpty() && timeBlock.getState().equals("RESERVADO")) {
               throw new RuntimeException("Ya tiene una ficha reservada para este día.");
           }
      
           timeBlock.setState("RESERVADO");
           timeBlock.setPacient(pacient); 
           timeBlockRepository.save(timeBlock);
            return ResponseDTO.builder()
            .data(null)
            .success(true)
            .error(false)
            .message("Ficha medicas")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
            
        }
    }

    public ResponseDTO cancelarFicha(TimeBlockDTO dto,HttpServletRequest request){
        try {
           String pacientId = (String)request.getAttribute("userId");
        System.out.println("++++++++++++++++++++++"+pacientId+"+++++++++++++++="+dto.getFichaId());
           Optional<TimeBlock> optionalTimeBlock = timeBlockRepository.findById(dto.getFichaId());
           TimeBlock timeBlock = optionalTimeBlock.orElseThrow(() -> new RuntimeException("Ficha no encontrada"));

           if ("CANCELADO".equals(timeBlock.getState())) {
              throw new RuntimeException("La ficha esta cancelado");
          
           }
           if ("DISPONIBLE".equals(timeBlock.getState())) {
            throw new RuntimeException("La ficha esta disponible");
        
            }

       
      
           timeBlock.setState("CANCELADO");
          // timeBlock.setPacient(pacient); 
           timeBlockRepository.save(timeBlock);
            return ResponseDTO.builder()
            .data(null)
            .success(true)
            .error(false)
            .message("Ficha cancelada")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
            
        }
    }


     public ResponseDTO getCitaByPacientID(HttpServletRequest request){
     
        try {

           String pacientId = (String)request.getAttribute("userId");
           
           List<Map<String, Object>> timeBlocks = timeBlockRepository.findAllByPacientCi(Integer.parseInt(pacientId)).stream().map(record -> {
                Map<String,Object> citasRecord = new HashMap<>();
                citasRecord.put("id",record.getId());
                citasRecord.put("date",record.getDate().toString());
                citasRecord.put("startTime",record.getStartTime().toString());
                citasRecord.put("status",record.getState());
                citasRecord.put("dayWeek",record.getOpeningHour().getDayWeek());
                citasRecord.put("idDoctor",record.getOpeningHour().getCi_doctor());
                citasRecord.put("nameDoctor",record.getOpeningHour().getDoctor().getUser().getName());
                citasRecord.put("speciality",record.getOpeningHour().getDoctor().getSpeciality().getName());
                citasRecord.put("idPacient",record.getPacient().getCi());
                citasRecord.put("namePacient",record.getPacient().getUser().getName());

                return citasRecord;
           })
           .collect(Collectors.toList());

           System.out.println(timeBlocks);
            return ResponseDTO.builder()
            .data(timeBlocks)
            .success(true)
            .error(false)
            .message("Usuarios obtenidos")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }


}
