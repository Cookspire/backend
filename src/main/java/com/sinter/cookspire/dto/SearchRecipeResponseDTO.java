package com.sinter.cookspire.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRecipeResponseDTO {

    private List<RecipeDTO> recipe;

    private int maxPageNumber;

}
