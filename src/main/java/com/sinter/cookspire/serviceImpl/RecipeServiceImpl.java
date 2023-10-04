package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
        Optional<Post> chkPost = postRepo.findById(request.getPostId());
        if (chkPost.isEmpty()) {
            logger.error("Error occured while persisting recipe.");
            logger.info("Exit from persisting recipe.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            recipeEntity.setPost(chkPost.get());
        recipeEntity.setInstruction(request.getInstructions());

        recipeEntity.setName(request.getName());

        if (request.getDifficulty() == null && chkRecipe.isPresent())
            recipeEntity.setLevel(chkRecipe.get().getLevel());
        else if (request.getDifficulty() == null)
            recipeEntity.setLevel(Level.UNKNOWN);
        else
            recipeEntity.setLevel(request.getDifficulty());

        recipeEntity.setUpdatedOn(LocalDateTime.now());
        long recipeId = recipeRepo.save(recipeEntity).getId();
        logger.info("Exit from persisting recipe.");
        return new RecipeDTO(recipeId, recipeEntity.getInstruction(), recipeEntity.getPost().getId(),
                recipeEntity.getLevel(), recipeEntity.getName(), recipeEntity.getCreatedOn(),
                recipeEntity.getUpdatedOn());
    }

    @Override
    public RecipeDTO fetchRecipe(@Valid Long postId) {
        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {
            Optional<Recipe> chkRecipe = recipeRepo.findByPost(chkPost.get());
            if (chkRecipe.isEmpty()) {
                return new RecipeDTO();
            }
            Recipe recipeEntity = chkRecipe.get();
            return new RecipeDTO(recipeEntity.getId(), recipeEntity.getInstruction(), recipeEntity.getPost().getId(),
                    recipeEntity.getLevel(), recipeEntity.getName(), recipeEntity.getCreatedOn(),
                    recipeEntity.getUpdatedOn());
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
    public List<RecipeDTO> fetchAllRecipe() {
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllRecipe'");
    }

}
