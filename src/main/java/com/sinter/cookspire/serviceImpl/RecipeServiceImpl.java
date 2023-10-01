package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.RecipeService;

import jakarta.validation.Valid;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Override
    public RecipeDTO persistRecipe(RecipeDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistRecipe'");
    }

    @Override
    public RecipeDTO fetchRecipe(@Valid Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchRecipe'");
    }

    @Override
    public ResponseDTO deleteRecipe(@Valid Long recipeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecipe'");
    }

    @Override
    public List<RecipeDTO> fetchAllRecipe() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllRecipe'");
    }

}
