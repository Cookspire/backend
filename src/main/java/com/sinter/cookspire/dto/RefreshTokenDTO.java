package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenDTO {

    @Nullable
    private long id;

    private String token;

    private String email;

    private LocalDateTime expiryTime;
}
