package com.parcial.parcialbackend.DTO;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PacientDTO {
    Integer ci; //ci del medico
    String name;
    String email;
    String password;
    Integer phone;
    String address;
    LocalDate dateOfBirth;  //fecha de nacimiento
    Integer age; //edad
    String sexo;

    
}
