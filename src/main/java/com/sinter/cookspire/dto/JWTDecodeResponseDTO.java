package com.sinter.cookspire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTDecodeResponseDTO {

    private String payload;

    private boolean isValid;

}
