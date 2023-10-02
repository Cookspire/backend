package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepliesDTO {

    @Nullable
    private long id;

    private long createdBy;

    private String content;

    private long commentId;

    @Nullable
    private int likes;

    @Nullable
    private int dislikes;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;

}
