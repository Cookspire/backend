package com.sinter.cookspire.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.sinter.cookspire.utils.Level;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    public Map<String, String> isEmpty() {
        return new HashMap<String, String>();
    }
}
