package com.parcial.parcialbackend.DTO;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpeningHourDTO {
    private String dayWeek;  //dia de ma semano Lunes, Martes, Miercoles, Jueves, Viernes
    private String turn;    
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer doctorId;
    private String nameDoctor;
    private String emTimel;
}
