package com.sinter.cookspire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageRequestDTO {
    
    private String type;

    private String name;

    private byte[] imageData;

    private long id;
}
