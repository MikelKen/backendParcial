package com.parcial.parcialbackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialtyDTO {
    
    String name;

    String description;
}
