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

    public UserResponseDTO() {

    }

    public UserResponseDTO(String username, String email, String country, Boolean isVerified, String imageName,
            String imageType,
            byte[] imageData) {
        this.username = username;
        this.email = email;
        this.country = country;
        this.isVerified = isVerified;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

}
