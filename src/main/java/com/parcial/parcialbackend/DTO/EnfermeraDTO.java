package com.parcial.parcialbackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnfermeraDTO {
    Integer ci; 
    String name;
    String email;
    String password;
    Integer phone;
    String address;
    Integer numberLicense;
}
