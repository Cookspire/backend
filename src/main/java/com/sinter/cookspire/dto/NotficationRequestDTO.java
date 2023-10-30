package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotficationRequestDTO {

    private long id;

    private long fromUser;

    private long toUser;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @Nullable
    private String comments;

    private Boolean isShown;

}
