package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RecipeResponseDTO;
import com.sinter.cookspire.dto.SearchRecipeRequestDTO;
import com.sinter.cookspire.dto.SearchRequestDTO;
import com.sinter.cookspire.dto.SearchSuggestionDTO;
import com.sinter.cookspire.dto.UserResponseDTO;

public interface SearchService {

    SearchSuggestionDTO searchSuggestions(SearchRequestDTO request);

    List<RecipeResponseDTO> searchRecipe(SearchRequestDTO request);

    List<UserResponseDTO> searchUser(SearchRequestDTO request);

    boolean searchPaginationRecipe(SearchRecipeRequestDTO request);

}
