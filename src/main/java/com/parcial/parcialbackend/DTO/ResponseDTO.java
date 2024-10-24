package com.parcial.parcialbackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private Object data;
    private boolean success;
    private boolean error;
    private String message;
}
