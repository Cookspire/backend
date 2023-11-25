package com.sinter.cookspire.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {

    @Nullable
    private long id;

    private String content;

    private long createdBy;

    private UserDTO createdUser;

    @Nullable
    private long likes;

    @Nullable
    private long dislikes;

    private boolean hasLiked;

    private boolean hasDisliked;

    @Nullable
    private LocalDateTime createdOn;

    @Nullable
    private LocalDateTime updatedOn;

    private String imageName;

    private String imageType;

    private byte[] imageData;

    private RecipeResponseDTO recipeDetails;

    public PostDTO(long id, String content, UserDTO createdUser, long likes, long dislikes, boolean hasLiked,
            boolean hasDisliked, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.id = id;
        this.content = content;
        this.createdUser = createdUser;
        this.likes = likes;
        this.dislikes = dislikes;
        this.hasLiked = hasLiked;
        this.hasDisliked = hasDisliked;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public PostDTO(long id, String content, UserDTO createdUser, long likes, long dislikes, boolean hasLiked,
            boolean hasDisliked, LocalDateTime createdOn, LocalDateTime updatedOn, String imageName, String imageType,
            byte[] imageData, RecipeResponseDTO recipeDetails) {
        this.id = id;
        this.content = content;
        this.createdUser = createdUser;
        this.likes = likes;
        this.dislikes = dislikes;
        this.hasLiked = hasLiked;
        this.hasDisliked = hasDisliked;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.recipeDetails = recipeDetails;
    }

}
