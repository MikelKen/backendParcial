package com.parcial.parcialbackend.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreConsultationDTO {

    private String arterialPressure;  // presion arterial
    private BigDecimal temperature;
    private Integer heartRate;//frecuencia cardiaca
    private BigDecimal weight;  //peso
    private LocalDate date;
    private Integer pacientId;
    private Integer citaId;//id de la cita
    
}
