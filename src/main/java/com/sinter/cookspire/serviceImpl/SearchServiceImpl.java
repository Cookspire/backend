package com.sinter.cookspire.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.RecipeResponseDTO;
import com.sinter.cookspire.dto.SearchRecipeRequestDTO;
import com.sinter.cookspire.dto.SearchRecipeResponseDTO;
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
    public SearchRecipeResponseDTO searchPaginationRecipe(SearchRecipeRequestDTO request) {

        SearchRecipeResponseDTO response = new SearchRecipeResponseDTO();
        Pageable pagination = PageRequest.of(request.getCurrentPageNumber(), 20);

        Page<Recipe> recipeFilter = recipeRepo.filterRecipe(
                request.getDietPlan(), request.getFromTime(), request.getToTime(), request.getCourse(),
                request.getCuisine(),
                request.getQuery(), pagination);

        List<RecipeDTO> recipes = new ArrayList<>();
        for (var recipeEntity : recipeFilter) {
            recipes.add(
                    new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(), recipeEntity.getName(),
                            recipeEntity.getLevel(),
                            recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                            recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                            recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                            0, recipeEntity.getImageName(), recipeEntity.getImageType(),
                            recipeEntity.getImageData()));
        }

        response.setRecipe(recipes);
        response.setMaxPageNumber(recipeFilter.getTotalPages() - 1);

        return response;
    }

}
