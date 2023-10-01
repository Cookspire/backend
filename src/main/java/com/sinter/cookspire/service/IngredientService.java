package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.IngredientDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface IngredientService {

    IngredientDTO persistIngredient(IngredientDTO request);

    IngredientDTO fetchIngredient(@Valid Long userId);

    ResponseDTO deleteIngredient(@Valid Long ingredientId);

    List<IngredientDTO> fetchAllIngredient();

}
