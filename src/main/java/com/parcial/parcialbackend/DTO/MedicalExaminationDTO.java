package com.parcial.parcialbackend.DTO;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalExaminationDTO {
    LocalDate date;
    String type; //tipo de examen
    String result; //resultados
    Integer pacientId; //ci del paciente
    Integer consultId;
}
