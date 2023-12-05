package com.sinter.cookspire.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RecipeResponseDTO;
import com.sinter.cookspire.dto.SearchRequestDTO;
import com.sinter.cookspire.dto.SearchSuggestionDTO;
import com.sinter.cookspire.dto.UserResponseDTO;
import com.sinter.cookspire.entity.Recipe;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.repository.IngredientRepository;
import com.sinter.cookspire.repository.RecipeRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.RecipeService;
import com.sinter.cookspire.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    IngredientRepository ingredientRepo;

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public SearchSuggestionDTO searchSuggestions(SearchRequestDTO request) {

        SearchSuggestionDTO response = new SearchSuggestionDTO();

        List<Users> userFilter = userRepo.filterUsers(request.getQuery());

        List<Recipe> recipeFilter = recipeRepo.filterRecipeAndIngredient(request.getQuery());

        logger.info("SQL filter for search query complete.");

        List<UserResponseDTO> userResponse = new ArrayList<UserResponseDTO>();
        for (var user : userFilter) {
            userResponse.add(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCountry(),
                    user.isVerified(), user.getBio(), user.getCreatedOn(), user.getUpdatedOn(), user.getImageName(),
                    user.getImageType(), user.getImageData()));
        }

        List<RecipeResponseDTO> recipeResponse = new ArrayList<RecipeResponseDTO>();
        for (var recipe : recipeFilter) {
            recipeResponse.add(recipeService.fetchRecipeByIngredient(recipe.getId()));
        }

        response.setRecipe(recipeResponse);

        response.setUsers(userResponse);

        logger.info("Exit from filter suggestion.");

        return response;

    }

    @Override
    public List<RecipeResponseDTO> searchRecipe(SearchRequestDTO request) {

        List<Recipe> recipeFilter = recipeRepo.filterRecipe(request.getQuery());
        logger.info("SQL filter for search query complete.");
        List<RecipeResponseDTO> recipeResponse = new ArrayList<RecipeResponseDTO>();
        for (var recipe : recipeFilter) {
            recipeResponse.add(recipeService.fetchRecipeByIngredient(recipe.getId()));
        }
        logger.info("Exit from recipe filter.");
        return recipeResponse;

    }

    @Override
    public List<UserResponseDTO> searchUser(SearchRequestDTO request) {

        List<Users> userFilter = userRepo.filterUsers(request.getQuery());

        List<UserResponseDTO> userResponse = new ArrayList<UserResponseDTO>();
        for (var user : userFilter) {
            userResponse.add(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCountry(),
                    user.isVerified(), user.getBio(), user.getCreatedOn(), user.getUpdatedOn(), user.getImageName(),
                    user.getImageType(), user.getImageData()));
        }

        return userResponse;
    }

}
