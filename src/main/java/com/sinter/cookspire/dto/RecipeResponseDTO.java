package com.sinter.cookspire.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {
    
    private RecipeDTO recipe;

    private List<IngredientDTO> ingredient;

    private int pageNumber;

}
