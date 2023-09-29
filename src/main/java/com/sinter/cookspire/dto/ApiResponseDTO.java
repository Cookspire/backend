package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO {

    private String guid;

    private Integer statusCode;

    private String errorMessage;

    private LocalDateTime timestamp;

}
