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

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
