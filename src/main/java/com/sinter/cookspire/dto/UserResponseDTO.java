package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private long id;

    private String username;

    private String email;

    private String country;

    private Boolean isVerified;

    private String bio;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String imageName;

    private String imageType;

    private byte[] imageData;

}
