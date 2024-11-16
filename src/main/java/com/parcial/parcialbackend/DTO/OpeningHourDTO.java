package com.parcial.parcialbackend.DTO;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpeningHourDTO {
    private Integer id;
    private String dayWeek;
    private String turn;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer doctorId;
    private String nameDoctor;
    private String emTimel;
}
