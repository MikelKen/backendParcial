package com.parcial.parcialbackend.DTO;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SheduleDTO {
    private Integer id;
    private String dayWeek;        // Día de la semana
    private String turn;           // Turno (Mañana, Tarde, Noche, etc.)
    private LocalTime startTime;   // Hora de inicio
    private LocalTime endTime;     // Hora de fin
    private Integer doctorId;      // ID del doctor
    private String doctorName;     // Nombre del doctor
}
