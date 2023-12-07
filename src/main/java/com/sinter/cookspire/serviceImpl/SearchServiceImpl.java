package com.sinter.cookspire.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RecipeResponseDTO;
import com.sinter.cookspire.dto.SearchRecipeRequestDTO;
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

        List<UserResponseDTO> userResponse = new ArrayList<UserResponseDTO>();

        List<String> recipeResponse = new ArrayList<String>();
        if (request.getQuery().trim().length() == 0) {

            response.setRecipe(recipeResponse);

            response.setUsers(userResponse);

            response.setQuery("");
            return response;
        }

        List<Users> userFilter = userRepo.filterUsers(request.getQuery());

        List<String> recipeFilter = recipeRepo.filterRecipeAndIngredient(request.getQuery());

        logger.info("SQL filter for search query complete.");

        for (var user : userFilter) {
            userResponse.add(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCountry(),
                    user.isVerified(), user.getBio(), user.getCreatedOn(), user.getUpdatedOn(), user.getImageName(),
                    user.getImageType(), user.getImageData()));
        }

        for (var recipe : recipeFilter) {
            recipeResponse.add(recipe.trim());
        }

        response.setRecipe(recipeResponse);

        response.setUsers(userResponse);

        response.setQuery(request.getQuery());

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

    @Override
    public boolean searchPaginationRecipe(SearchRecipeRequestDTO request) {
        // TODO Auto-generated method stub

        Pageable pagination = PageRequest.of(1, 20, Sort.by("name").ascending());

        // List<Recipe> recipeFilter = new ArrayList<Recipe>();
        // if (request.getDietPlan().length() > 0 && request.getTiming().length() > 0) {
        //     recipeFilter = recipeRepo.filterRecipeByDietAndTiming(request.getQuery(),
        //             request.getDietPlan(), request.getFromTime(), request.getToTime(), pagination);
        // } else if (request.getDietPlan().length() > 0) {
        //     recipeFilter = recipeRepo.filterRecipeByDiet(request.getQuery(), request.getDietPlan());
        // } else if (request.getTiming().length() > 0) {
        //     recipeFilter = recipeRepo.filterRecipeByTiming(request.getQuery(), request.getTiming());
        // }
        // List<RecipeResponseDTO> recipeResponse = new ArrayList<RecipeResponseDTO>();
        // for (var recipe : recipeFilter) {
        //     recipeResponse.add(recipeService.fetchRecipeByIngredient(recipe.getId()));
        // }

        return false;
    }

}
