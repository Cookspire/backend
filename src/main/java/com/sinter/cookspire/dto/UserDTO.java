package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class UserDTO {

    @Nullable
    private long id;

    private String username;

    private String email;

    private String password;

    private String country;

    private Boolean isVerified;

    private String bio;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String oldPassword;

    private String imageName;

    private String imageType;

    private byte[] imageData;

    public UserDTO(long id, String username, String email, String password, String country, Boolean isVerified,
            String bio, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
        this.isVerified = isVerified;
        this.bio = bio;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public UserDTO(long id, String username, String email, String country, Boolean isVerified, String bio,
            LocalDateTime createdOn, LocalDateTime updatedOn, String imageName, String imageType, byte[] imageData) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.country = country;
        this.isVerified = isVerified;
        this.bio = bio;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public UserDTO() {
        
    }

    

}
