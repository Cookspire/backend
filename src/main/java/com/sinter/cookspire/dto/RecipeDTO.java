package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import com.sinter.cookspire.utils.Level;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class RecipeDTO {

    @Nullable
    private long id;

    private String instructions;

    private long postId;

    private Level difficulty;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;
}
