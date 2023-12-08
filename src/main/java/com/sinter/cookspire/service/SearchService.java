package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RecipePaginationDTO;
import com.sinter.cookspire.dto.SearchRecipePageDTO;
import com.sinter.cookspire.dto.SearchRecipeRequestDTO;
import com.sinter.cookspire.dto.SearchRecipeResponseDTO;
import com.sinter.cookspire.dto.SearchRequestDTO;
import com.sinter.cookspire.dto.SearchSuggestionDTO;
import com.sinter.cookspire.dto.UserResponseDTO;

public interface SearchService {

    SearchSuggestionDTO searchSuggestions(SearchRequestDTO request);

    RecipePaginationDTO searchRecipe(SearchRecipePageDTO request);

    List<UserResponseDTO> searchUser(SearchRequestDTO request);

    SearchRecipeResponseDTO searchPaginationRecipe(SearchRecipeRequestDTO request);

}
