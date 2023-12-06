package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.RecipePaginationDTO;
import com.sinter.cookspire.dto.RecipeResponseDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface RecipeService {

    RecipeDTO persistRecipe(RecipeDTO request);

    RecipeResponseDTO fetchRecipeByPost(@Valid Long postId);

    ResponseDTO deleteRecipe(@Valid Long recipeId);

    List<RecipeDTO> filterAllRecipe();

    List<String> fetchAllCuisine();

    RecipePaginationDTO fetchRecipesByCuisine(String cuisine, int pageNumber);

    List<String> fetchAllCourse();

    RecipePaginationDTO fetchRecipesByCourse(String course, int pageNumber);

    RecipeDTO fetchRecipe(long recipeId);

    RecipeResponseDTO fetchRecipeByIngredient(long recipeId);
    
}
