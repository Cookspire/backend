package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class IngredientDTO {
    @Nullable
    private long id;

    private String item;

    private int quantity;

    private String units;

    private long recipeId;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;
}
