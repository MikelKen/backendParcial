package com.parcial.parcialbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    Integer ci;
    String name;
    String email;
    String password;
    Integer phone;
    String address;
}
