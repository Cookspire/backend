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
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sinter.cookspire.dto.RecipeDTO;
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
    public ResponseEntity<?> fetchAllRecipe() {
        logger.info("Entering fetchAll recipe logic");
        return new ResponseEntity<>(recipeService.fetchAllRecipe(), HttpStatus.OK);
    }

    @GetMapping(value = "/load/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> LoadAllRecipe() {
        logger.info("Entering loading recipe logic");

        String csvFile = "D:\\WebDevProjects\\TUD_Projects\\WEB-9810_Projects\\LabWork\\dataset\\test_dataset\\recipe_test.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {

                RecipeDTO recipeDTO = new RecipeDTO(0, record[7], record[10], Level.UNKNOWN,
                        record[5], record[4], record[2], record[6], Integer.parseInt(record[11]),
                        Integer.parseInt(record[1]), LocalDateTime.now(), LocalDateTime.now(), false, 0, record[15],
                        "url", null);

                System.out.println(recipeDTO.getCook_time_mins());
                System.out.println(recipeDTO.getUpdatedOn());
                System.out.println(recipeDTO.getPostId());
                System.out.println(recipeDTO.getInstructions());
                recipeService.persistRecipe(recipeDTO);
                // Now, you can perform the database insertion logic here
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
