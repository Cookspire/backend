package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInteractionDTO {

    @Nullable
    private long id;

    @NonNull
    private long createdBy;

    @NonNull
    private long postId;

    @NonNull
    private boolean isLiked;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;
}