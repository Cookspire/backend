package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface RecipeService {

    RecipeDTO persistRecipe(RecipeDTO request);

    RecipeDTO fetchRecipeByPost(@Valid Long postId);

    ResponseDTO deleteRecipe(@Valid Long recipeId);

    List<RecipeDTO> filterAllRecipe();

    List<String> fetchAllCuisine();

    List<RecipeDTO> fetchRecipesByCuisine(String cuisine);

    List<String> fetchAllCourse();

    List<RecipeDTO> fetchRecipesByCourse(String course);

    public RecipeDTO fetchRecipe(long recipeId);
    
}
