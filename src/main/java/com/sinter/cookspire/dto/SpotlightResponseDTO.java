package com.sinter.cookspire.dto;

import lombok.Data;

@Data
public class SpotlightResponseDTO {
    
    private boolean isfollower;

    private boolean isfollowing;

    private long id;

    private String username;

    private String email;

    private String country;

    private Boolean isVerified;

    private String bio;

    private String imageName;

    private String imageType;

    private byte[] imageData;

}
