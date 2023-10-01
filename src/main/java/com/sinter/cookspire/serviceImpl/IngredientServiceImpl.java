package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.IngredientDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.IngredientService;

import jakarta.validation.Valid;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Override
    public IngredientDTO persistIngredient(IngredientDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistIngredient'");
    }

    @Override
    public IngredientDTO fetchIngredient(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchIngredient'");
    }

    @Override
    public ResponseDTO deleteIngredient(@Valid Long ingredientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteIngredient'");
    }

    @Override
    public List<IngredientDTO> fetchAllIngredient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllIngredient'");
    }

}
