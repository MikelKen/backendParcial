package com.parcial.parcialbackend.DTO;

import java.time.LocalDate;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeBlockDTO {
    //para crear las fichas por medico segun su hora de atencion
    private LocalDate date;
    private Integer doctorCi;

    //para reservar fichas por el paciente
    private Integer fichaId;
    private Integer pacientCi;

    //para prueba
    private String date1;
    private String day1;
    private String time1;
    

}
