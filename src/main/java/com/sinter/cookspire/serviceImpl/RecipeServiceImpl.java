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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Recipe;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.RecipeRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.RecipeService;
import com.sinter.cookspire.utils.Level;

import jakarta.validation.Valid;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    PostRepository postRepo;

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public RecipeDTO persistRecipe(RecipeDTO request) {
        Optional<Recipe> chkRecipe = recipeRepo.findById(request.getId());
        Recipe recipeEntity = new Recipe();
        if (request.getId() != 0 && chkRecipe.isPresent()) {
            recipeEntity.setCreatedOn(chkRecipe.get().getCreatedOn());
            recipeEntity.setId(chkRecipe.get().getId());
        } else if (request.getId() == 0)
            recipeEntity.setCreatedOn(LocalDateTime.now());
        else {
            logger.error("Error occured while persisting recipe.");
            logger.info("Exit from persisting recipe.");
            throw new ApplicationException(msgSrc.getMessage("Recipe.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }
        if (request.getPostId() != 0) {
            Optional<Post> chkPost = postRepo.findById(request.getPostId());
            if (chkPost.isEmpty()) {
                logger.error("Error occured while persisting recipe.");
                logger.info("Exit from persisting recipe.");
                throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                        HttpStatus.NOT_FOUND);
            } else
                recipeEntity.setPost(chkPost.get());
        } else {
            recipeEntity.setPost(null);
        }

        recipeEntity.set_Verified(request.is_Verified());
        recipeEntity.setCook_time_mins(request.getCook_time_mins());
        recipeEntity.setCourse(request.getCourse());
        recipeEntity.setDescription(request.getDescription());
        recipeEntity.setCuisine(request.getCuisine());
        recipeEntity.setDiet(request.getDiet());

        if (request.getImageName() != null) {
            recipeEntity.setImageName(request.getImageName());
        }

        if (request.getImageType() != null) {
            recipeEntity.setImageType(request.getImageType());
        }

        if (request.getImageData() != null) {
            recipeEntity.setImageData(request.getImageData());
        }

        recipeEntity.setInstruction(request.getInstructions());

        recipeEntity.setName(request.getName());
        recipeEntity.setPrep_time_mins(request.getPrep_time_mins());

        if (request.getDifficulty() == null && chkRecipe.isPresent())
            recipeEntity.setLevel(chkRecipe.get().getLevel());
        else if (request.getDifficulty() == null)
            recipeEntity.setLevel(Level.UNKNOWN);
        else
            recipeEntity.setLevel(request.getDifficulty());

        recipeEntity.setUpdatedOn(LocalDateTime.now());
        long recipeId = recipeRepo.save(recipeEntity).getId();
        logger.info("Exit from persisting recipe.");
        return new RecipeDTO(recipeId, recipeEntity.getInstruction(), recipeEntity.getName(), recipeEntity.getLevel(),
                recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                0, recipeEntity.getImageName(), recipeEntity.getImageType(), recipeEntity.getImageData());
    }

    @Override
    public RecipeDTO fetchRecipeByPost(@Valid Long postId) {
        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {
            Optional<Recipe> chkRecipe = recipeRepo.findByPost(chkPost.get());
            if (chkRecipe.isEmpty()) {
                return new RecipeDTO();
            }
            Recipe recipeEntity = chkRecipe.get();
            return new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(), recipeEntity.getName(),
                    recipeEntity.getLevel(),
                    recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                    recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                    recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                    recipeEntity.getPost().getId(), recipeEntity.getImageName(), recipeEntity.getImageType(),
                    recipeEntity.getImageData());
        }

        else {
            logger.warn("Recipe not found.");
            logger.info("Exit from fetching Recipe.");
            throw new ApplicationException(msgSrc.getMessage("Recipe.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseDTO deleteRecipe(@Valid Long recipeId) {
        Optional<Recipe> chkRecipe = recipeRepo.findById(recipeId);

        if (chkRecipe.isPresent()) {
            recipeRepo.deleteById(recipeId);
            return new ResponseDTO("Recipe deleted Successfully");
        } else {
            logger.warn("Recipe not found");
            logger.info("Exit from deleting recipe.");
            throw new ApplicationException(msgSrc.getMessage("Recipe.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Main core logic where it should retrive all possible recipes
    @Override
    public List<RecipeDTO> filterAllRecipe() {

        throw new UnsupportedOperationException("Unimplemented method 'fetchAllRecipe'");
    }

    @Override
    public List<String> fetchAllCuisine() {
        logger.info("Entering into fetch all cuisine.");
        logger.info("Exit from fetch all cuisine.");
        return recipeRepo.findAllCuisines();
    }

    @Override
    public List<RecipeDTO> fetchRecipesByCuisine(String cuisine) {

        Pageable pagination= PageRequest.of(1, 20, Sort.by("name").ascending());

        List<RecipeDTO> response = new ArrayList<RecipeDTO>();
        List<Recipe> recipesByCuisines = recipeRepo.findByCuisineIgnoreCase(cuisine,pagination);
        System.out.println("Recipe fetch done!!");
        for (var recipe : recipesByCuisines) {
            Recipe recipeEntity = recipe;
            RecipeDTO recipeDTO = new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(),
                    recipeEntity.getName(), recipeEntity.getLevel(),
                    recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                    recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                    recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                    0, recipeEntity.getImageName(), recipeEntity.getImageType(), recipeEntity.getImageData());
            response.add(recipeDTO);
        }

        return response;
    }

    @Override
    public List<String> fetchAllCourse() {
        logger.info("Entering into fetch all courses.");
        logger.info("Exit from fetch all courses.");
        return recipeRepo.findAllCourses();
    }

    @Override
    public List<RecipeDTO> fetchRecipesByCourse(String course) {

        Pageable pagination= PageRequest.of(0, 20, Sort.by("name").ascending());

        List<RecipeDTO> response = new ArrayList<RecipeDTO>();
        List<Recipe> recipesByCourse = recipeRepo.findByCourseIgnoreCase(course, pagination);
        for (var recipe : recipesByCourse) {
            Recipe recipeEntity = recipe;
            RecipeDTO recipeDTO = new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(),
                    recipeEntity.getName(), recipeEntity.getLevel(),
                    recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                    recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                    recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                    0, recipeEntity.getImageName(), recipeEntity.getImageType(), recipeEntity.getImageData());
            response.add(recipeDTO);
        }

        return response;
    }

    @Override
    public RecipeDTO fetchRecipe(long recipeId) {
        Optional<Recipe> chkRecipe = recipeRepo.findById(recipeId);

        if (chkRecipe.isPresent()) {

            Recipe recipeEntity = chkRecipe.get();
            Long postId = recipeEntity.getPost() != null ? recipeEntity.getPost().getId() : 0;
            System.out.println(recipeEntity.getInstruction().length());
            return new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(), recipeEntity.getName(),
                    recipeEntity.getLevel(),
                    recipeEntity.getDescription(), recipeEntity.getCuisine(), recipeEntity.getCourse(),
                    recipeEntity.getDiet(), recipeEntity.getPrep_time_mins(), recipeEntity.getCook_time_mins(),
                    recipeEntity.getCreatedOn(), recipeEntity.getUpdatedOn(), recipeEntity.is_Verified(),
                    postId, recipeEntity.getImageName(), recipeEntity.getImageType(),
                    recipeEntity.getImageData());
        }

        else {
            logger.warn("Recipe not found.");
            logger.info("Exit from fetching Recipe.");
            throw new ApplicationException(msgSrc.getMessage("Recipe.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
