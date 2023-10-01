package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class CommentDTO {

    @Nullable
    private long id;

    private long createdBy;

    private String content;

    @Nullable
    private long likes;

    @Nullable
    private long dislikes;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;

}