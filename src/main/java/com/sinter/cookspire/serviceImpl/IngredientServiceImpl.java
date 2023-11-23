package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.IngredientDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Ingredient;
import com.sinter.cookspire.entity.Recipe;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.IngredientRepository;
import com.sinter.cookspire.repository.RecipeRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.IngredientService;

import jakarta.validation.Valid;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository ingredientRepo;

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<IngredientDTO> persistIngredient(List<IngredientDTO> request) {

        List<IngredientDTO> response = new ArrayList<IngredientDTO>();

        for (var ingredient : request) {

            Optional<Ingredient> chkIngredient = ingredientRepo.findById(ingredient.getId());

            Ingredient ingredientEntity = new Ingredient();

            if (ingredient.getId() != 0 && chkIngredient.isPresent()) {
                ingredientEntity.setCreatedOn(chkIngredient.get().getCreatedOn());
                ingredientEntity.setId(chkIngredient.get().getId());
            } else if (ingredient.getId() == 0)
                ingredientEntity.setCreatedOn(LocalDateTime.now());
            else {
                logger.error("Error occured while persisting ingredient.");
                logger.info("Exit from persisting ingredient.");
                throw new ApplicationException(msgSrc.getMessage("Ingredient.Error", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            }
            Optional<Recipe> chkRecipe = recipeRepo.findById(ingredient.getRecipeId());
            if (chkRecipe.isEmpty()) {
                logger.error("Error occured while persisting recipe.");
                logger.info("Exit from persisting recipe.");
                throw new ApplicationException(msgSrc.getMessage("Recipe.NotFound", null, Locale.ENGLISH),
                        HttpStatus.NOT_FOUND);
            } else
                ingredientEntity.setRecipes(chkRecipe.get());

            if (ingredient.getItem() == null && chkIngredient.isPresent())
                ingredientEntity.setItem(chkIngredient.get().getItem());
            else
                ingredientEntity.setItem(ingredient.getItem());

            if (ingredient.getQuantity() == null && chkIngredient.isPresent())
                ingredientEntity.setQuantity(chkIngredient.get().getQuantity());
            else
                ingredientEntity.setQuantity(ingredient.getQuantity());

            if (ingredient.getUnits() == null && chkIngredient.isPresent())
                ingredientEntity.setUnits(chkIngredient.get().getUnits());
            else
                ingredientEntity.setUnits(ingredient.getUnits());

            ingredientEntity.setUpdatedOn(LocalDateTime.now());

            long ingredientId = ingredientRepo.save(ingredientEntity).getId();

            response.add(new IngredientDTO(ingredientId, ingredientEntity.getItem(), ingredientEntity.getQuantity(),
                    ingredientEntity.getUnits(), ingredientEntity.getRecipes().getId(), ingredientEntity.getCreatedOn(),
                    ingredientEntity.getUpdatedOn()));

        }

        logger.info("Exit from persisting recipe.");
        return response;

    }

    @Override
    public ResponseDTO deleteIngredient(@Valid Long ingredientId) {

        Optional<Ingredient> chkIngredient = ingredientRepo.findById(ingredientId);
        if (chkIngredient.isPresent()) {
            ingredientRepo.deleteById(ingredientId);
            return new ResponseDTO("Ingredient deleted Successfully");
        } else {
            logger.warn("Ingredient not found");
            logger.info("Exit from deleting Ingredient.");
            throw new ApplicationException(msgSrc.getMessage("Ingredient.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<IngredientDTO> fetchAllIngredient(long recipeId) {

        List<IngredientDTO> response = new ArrayList<IngredientDTO>();

        Optional<Recipe> chkRecipe = recipeRepo.findById(recipeId);

        if (chkRecipe.isEmpty()) {
            logger.error("Error occured while persisting recipe.");
            logger.info("Exit from persisting recipe.");
            throw new ApplicationException(msgSrc.getMessage("Recipe.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else {
            List<Ingredient> ingredients = ingredientRepo.findByRecipes(chkRecipe.get());

            for (var ingredient : ingredients) {
                response.add(new IngredientDTO(ingredient.getId(), ingredient.getItem(), ingredient.getQuantity(),
                        ingredient.getUnits(), ingredient.getRecipes().getId(), ingredient.getCreatedOn(),
                        ingredient.getUpdatedOn()));
            }

            return response;

        }

    }

}
