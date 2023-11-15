package com.sinter.cookspire.controller;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sinter.cookspire.dto.RecipeDTO;
import com.sinter.cookspire.repository.RecipeRepository;
import com.sinter.cookspire.service.RecipeService;
import com.sinter.cookspire.utils.Level;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    MessageSource msgSrc;

    @Autowired
    RecipeRepository recipeRepo;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody RecipeDTO request) {
        logger.info("Entering persist recipe logic");
        return new ResponseEntity<>(recipeService.persistRecipe(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/recipe/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipe(@PathParam(value = "id") @Valid Long postId) {
        logger.info("Entering fetch recipe logic");
        return new ResponseEntity<>(recipeService.fetchRecipeByPost(postId), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/cuisine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllCuisine() {
        logger.info("Entering fetch all cuisine logic");
        return new ResponseEntity<>(recipeService.fetchAllCuisine(), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllCourse() {
        logger.info("Entering fetch all course logic");
        return new ResponseEntity<>(recipeService.fetchAllCourse(), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/recipe/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipeByCourse(@RequestParam(value = "name") String course) {
        logger.info("Entering fetch recipes by course logic");
        return new ResponseEntity<>(recipeService.fetchRecipesByCourse(course), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/recipe/cuisine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipeByCuisine(@RequestParam(value = "name") String cuisine) {
        logger.info("Entering fetch recipes by cuisine logic");
        return new ResponseEntity<>(recipeService.fetchRecipesByCuisine(cuisine), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchRecipe(@PathParam(value = "id") long recipeId) {
        logger.info("Entering fetch recipe logic");
        return new ResponseEntity<>(recipeService.fetchRecipe(recipeId), HttpStatus.OK);
    }

    @GetMapping(value = "/load/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> LoadAllRecipe() 
    {
        logger.info("Entering loading recipe logic");

        String csvFile = "D:\\WebDevProjects\\TUD_Projects\\WEB-9810_Projects\\LabWork\\dataset\\test_dataset\\recipe_test.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                record[5] = record[5].replaceAll("[^a-zA-Z ]", "");
                String shortDesc = record[5].substring(0, Math.min(record[5].length(), 150));

                String prepare_time = record[11].length() > 0 ? record[11] : "0";
                String cooking_time = record[1].length() > 0 ? record[1] : "0";
                String name = record[10].replaceAll("[^a-zA-Z ]", "");
                RecipeDTO recipeDTO = new RecipeDTO(0, record[7], name, Level.UNKNOWN,
                        shortDesc, record[4], record[2], record[6], Integer.parseInt(prepare_time),
                        Integer.parseInt(cooking_time), LocalDateTime.now(), LocalDateTime.now(), false, 0, record[15],
                        "url", null);
                recipeService.persistRecipe(recipeDTO);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            return null;
        }

        return new ResponseEntity<>("Load Success!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRecipe(@PathParam(value = "recipeId") @Valid Long recipeId) {
        logger.info("Entering delete recipe logic");
        return new ResponseEntity<>(recipeService.deleteRecipe(recipeId), HttpStatus.OK);
    }

}
