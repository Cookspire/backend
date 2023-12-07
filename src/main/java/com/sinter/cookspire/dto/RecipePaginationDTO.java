package com.sinter.cookspire.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipePaginationDTO {

    private List<RecipeDTO> recipe;

    private int maxPageNumber;
}
