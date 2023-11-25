package com.sinter.cookspire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptorDTO {
    
    private String salt;

    private String hashText;
}
