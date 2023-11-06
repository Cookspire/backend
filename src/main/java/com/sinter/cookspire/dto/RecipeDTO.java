package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import com.sinter.cookspire.utils.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    private long id;

    private String instructions;

    private String name;

    private Level difficulty;

    private String description;

    private String cuisine;

    private String course;

    private String diet;

    private Integer prep_time_mins;

    private Integer cook_time_mins;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private boolean is_Verified;

    private long postId;

    private String imageName;

    private String imageType;

    private byte[] imageData;


}
