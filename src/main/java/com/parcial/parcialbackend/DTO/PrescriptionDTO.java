package com.parcial.parcialbackend.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionDTO {
    private String medicine; //medicamento
    private String dosis; 
    private String frecuency; //frecuencia
    private String duration;
    private String observation;
    private Integer consultId;
}
