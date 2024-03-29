package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

    @Nullable
    private long id;

    private long createdBy;

    private long postId;

    private String content;

    @Nullable
    private int likes;

    @Nullable
    private int dislikes;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;

}
