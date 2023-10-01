package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface RecipeService {

    RecipeDTO persistRecipe(RecipeDTO request);

    RecipeDTO fetchRecipe(@Valid Long postId);

    ResponseDTO deleteRecipe(@Valid Long recipeId);

    List<RecipeDTO> fetchAllRecipe();
    
}
