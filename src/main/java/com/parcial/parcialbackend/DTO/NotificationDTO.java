package com.parcial.parcialbackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {
    
    private Integer fichaIdrepro;  //id de la ficha reprogramada
    private Integer fichaIdNueva;  // id de la ficha nueva para reemplazar la reprogramada

    private String message;
}
