package com.sinter.cookspire.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchSuggestionDTO {

    List<RecipeResponseDTO> recipe;

    List<UserResponseDTO> users;

    String query;

}
