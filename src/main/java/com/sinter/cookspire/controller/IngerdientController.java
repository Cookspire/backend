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

import com.sinter.cookspire.dto.IngredientDTO;
import com.sinter.cookspire.service.IngredientService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
public class IngerdientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/ingredient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody IngredientDTO request) {
        logger.info("Entering persist ingredient logic");
        return new ResponseEntity<>(ingredientService.persistIngredient(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchIngredient(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering fetch ingredient logic");
        return new ResponseEntity<>(ingredientService.fetchIngredient(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllIngredient() {
        logger.info("Entering fetchAll ingredient logic");
        return new ResponseEntity<>(ingredientService.fetchAllIngredient(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteIngredient(@PathParam(value = "ingredientId") @Valid Long ingredientId) {
        logger.info("Entering delete ingredient logic");
        return new ResponseEntity<>(ingredientService.deleteIngredient(ingredientId), HttpStatus.OK);
    }

}
