package com.sinter.cookspire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.SearchRecipePageDTO;
import com.sinter.cookspire.dto.SearchRecipeRequestDTO;
import com.sinter.cookspire.dto.SearchRequestDTO;
import com.sinter.cookspire.service.SearchService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class SearchController {

    @Autowired
    SearchService searchService;

    Logger logger = LoggerFactory.getLogger(SearchController.class);

    @PostMapping(value = "/search/cookspire", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchSuggestion(@RequestBody SearchRequestDTO request) {
        logger.info("Entering suggestions search logic");
        return new ResponseEntity<>(searchService.searchSuggestions(request), HttpStatus.OK);
    }

    @PostMapping(value = "/search/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchRecipe(@RequestBody SearchRecipePageDTO request) {
        logger.info("Entering search recipe logic");
        return new ResponseEntity<>(searchService.searchRecipe(request), HttpStatus.OK);
    }

    @PostMapping(value = "/filter/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPaginationRecipe(@RequestBody SearchRecipeRequestDTO request) {
        logger.info("Entering search recipe logic");
        return new ResponseEntity<>(searchService.searchPaginationRecipe(request), HttpStatus.OK);
    }

    @PostMapping(value = "/search/people", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPeople(@RequestBody SearchRequestDTO request) {
        logger.info("Entering search people logic");
        return new ResponseEntity<>(searchService.searchUser(request), HttpStatus.OK);
    }

}
