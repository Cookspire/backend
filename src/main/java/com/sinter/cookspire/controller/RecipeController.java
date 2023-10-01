package com.sinter.cookspire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.service.RecipeService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody RecipeDTO request) {
        logger.info("Entering persist recipe logic");
        return new ResponseEntity<>(recipeService.persistRecipe(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipe(@PathParam(value = "postId") @Valid Long postId) {
        logger.info("Entering fetch recipe logic");
        return new ResponseEntity<>(recipeService.fetchRecipe(postId), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllRecipe() {
        logger.info("Entering fetchAll recipe logic");
        return new ResponseEntity<>(recipeService.fetchAllRecipe(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRecipe(@PathParam(value = "recipeId") @Valid Long recipeId) {
        logger.info("Entering delete recipe logic");
        return new ResponseEntity<>(recipeService.deleteRecipe(recipeId), HttpStatus.OK);
    }

}
