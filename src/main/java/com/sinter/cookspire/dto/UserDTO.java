package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    @Nullable
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String country;

    @NonNull
    private Boolean isVerified;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    
}
