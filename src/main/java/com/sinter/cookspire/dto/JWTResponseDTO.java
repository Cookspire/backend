package com.sinter.cookspire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDTO {

    private String email;

    private String accessToken;

    private String refresh;
    
}
