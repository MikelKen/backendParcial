package com.parcial.parcialbackend.DTO;


import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationDTO {

    LocalDate date;
    String diagnosis;
    String state;
    String observation;
    Integer doctorId;
    Integer preCosultId;
    Integer medicalRecordId;

}
