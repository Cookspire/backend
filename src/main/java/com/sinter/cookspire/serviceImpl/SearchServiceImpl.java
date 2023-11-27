package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.SearchRequestDTO;
import com.sinter.cookspire.entity.Ingredient;
import com.sinter.cookspire.entity.Recipe;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.repository.IngredientRepository;
import com.sinter.cookspire.repository.RecipeRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    IngredientRepository ingredientRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    @Override
    public boolean searchCookspire(SearchRequestDTO request) {

        List<Users> userFilter = userRepo.filterUsers(request.getQuery());

        List<Ingredient> ingredientsFilter = ingredientRepo.filterIngredient(request.getQuery());

        List<Recipe> recipeFilter = recipeRepo.filterRecipe(request.getQuery());

        System.out.println(userFilter.size());

        System.out.println(ingredientsFilter.size());

        System.out.println(recipeFilter.size());

        return true;

    }

}
