package com.parcial.parcialbackend.service;

import java.time.LocalTime;
import java.util.ArrayList;
//import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.OpeningHourDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Doctor;
import com.parcial.parcialbackend.entity.OpeningHour;
import com.parcial.parcialbackend.entity.TimeBlock;
import com.parcial.parcialbackend.repository.DoctorRepository;
import com.parcial.parcialbackend.repository.OpeningHourRepository;
import com.parcial.parcialbackend.repository.TimeBlockRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OpeningHourService {
    @Autowired
    private final OpeningHourRepository openingHourRepository;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final TimeBlockRepository timeBlockRepository;

    public ResponseDTO createOpeningHour(OpeningHourDTO dto){
        try {
            Doctor doctor = doctorRepository.findByCi(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));

            OpeningHour openingHour = OpeningHour.builder()
                                    .dayWeek(dto.getDayWeek())
                                    .turn(dto.getTurn())
                                    .startTime(dto.getStartTime())
                                    .endTime(dto.getEndTime())
                                    .ci_doctor(dto.getDoctorId())
                                    .doctor(doctor)
                                    .build();
            openingHourRepository.save(openingHour);
            
            return ResponseDTO.builder()
            .data(openingHour)
            .success(true)
            .error(false)
            .message("Opening Hour created successful ")
            .build();
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
            return ResponseDTO.builder()
            .data(null)
            .success(false)
            .error(true)
            .message("Opening hour error "+e)
            .build();
        }
    }

    public ResponseDTO createOpeningHourBlock(OpeningHourDTO dto){
        try {
            Doctor doctor = doctorRepository.findByCi(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found"));
     
            OpeningHour openingHour = OpeningHour.builder()
                                    .dayWeek(dto.getDayWeek())
                                    .turn(dto.getTurn())
                                    .startTime(dto.getStartTime())
                                    .endTime(dto.getEndTime())
                                    .ci_doctor(dto.getDoctorId())
                                    .doctor(doctor)
                                    .build();
           
            openingHourRepository.save(openingHour);

            List<TimeBlock> timeBlocks = new ArrayList<>();
            LocalTime currentStartTime = dto.getStartTime();
            System.out.println("insetrando");
            while (currentStartTime.isBefore(dto.getEndTime())){
                LocalTime currentEndTime = currentStartTime.plusHours(1);
                
                TimeBlock timeBlock = TimeBlock.builder()
                                    .startTime(currentStartTime)
                                    .endTime(currentEndTime)
                                    .openingHour(openingHour)
                                    .build();
                timeBlocks.add(timeBlock);
                System.out.println("insetrando");
                currentStartTime = currentEndTime;
            }
            timeBlockRepository.saveAll(timeBlocks);
            
            return ResponseDTO.builder()
            .data(openingHour)
            .success(true)
            .error(false)
            .message("Opening Hour created successful ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }


    public ResponseDTO getOpeningHourByDoctorId(Integer doctorCi){
        try {
           
            //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
           List<OpeningHour> openingHours = openingHourRepository.findByDoctorCi(doctorCi);


           List<OpeningHourDTO> openingHourDTOs = openingHours.stream().map(openingHour -> OpeningHourDTO.builder()
                        .doctorId(openingHour.getId())
                        .dayWeek(openingHour.getDayWeek())
                        .turn(openingHour.getTurn())
                        .startTime(openingHour.getStartTime())
                        .endTime(openingHour.getEndTime())
                        .doctorId(openingHour.getDoctor().getCi())
                        .nameDoctor(openingHour.getDoctor().getUser().getName())
                        //.emTimel(openingHour.getStartTime().format(timeFormatter))
                        .build()
           ).collect(Collectors.toList());

            return ResponseDTO.builder()
            .data(openingHourDTOs)
            .success(false)
            .error(true)
            .message(" ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); 
        }
    }
    
}
