package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String country;

    @NonNull
    private Boolean isVerified;

    @NonNull
    private String bio;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}
