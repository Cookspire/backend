package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkDTO {

    @Nullable
    private long id;

    private long postId;

    private long createdBy;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;

}
