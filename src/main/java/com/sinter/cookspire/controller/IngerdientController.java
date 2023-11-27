package com.sinter.cookspire.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.sinter.cookspire.dto.IngredientDTO;
import com.sinter.cookspire.service.IngredientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class IngerdientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/ingredient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistIngredient(@RequestBody List<IngredientDTO> request) {
        logger.info("Entering persist ingredient logic");
        return new ResponseEntity<>(ingredientService.persistIngredient(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllIngredient(@PathParam(value = "recipeId") @Valid Long recipeId) {
        logger.info("Entering fetchAll ingredient logic");
        return new ResponseEntity<>(ingredientService.fetchAllIngredient(recipeId), HttpStatus.OK);
    }

    @GetMapping(value = "/load/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> LoadAllRecipe() throws FileNotFoundException, IOException {
        logger.info("Entering loading recipe logic");

        String csvFile = "D:\\WebDevProjects\\TUD_Projects\\WEB-9810_Projects\\LabWork\\dataset\\test_dataset\\ing_test.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = csvReader.readAll();
            int recipeId = 1;
            for (String[] record : records) {
                // record[5] = record[5].replaceAll("[^a-zA-Z]", "");
                // String shortDesc = record[5].substring(0, Math.min(record[5].length(), 150));

                String prepare_item = record[2].replaceAll("[^a-zA-Z\\,\\/0-9 ]", "");
                String cooking_instructions = record[4].replaceAll("[^a-zA-Z\\,\\/0-9 ]", "");

                IngredientDTO ingredientDTO = new IngredientDTO(0, prepare_item, cooking_instructions, null, recipeId,
                        LocalDateTime.now(),
                        LocalDateTime.now());

                List<IngredientDTO> request = new ArrayList<IngredientDTO>();
                request.add(ingredientDTO);
                ingredientService.persistIngredient(request);
                recipeId++;

            }
        } catch (CsvException e) {
            e.printStackTrace();
            return null;
        }

        return new ResponseEntity<>("Load Success!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteIngredient(@PathParam(value = "ingredientId") @Valid Long ingredientId) {
        logger.info("Entering delete ingredient logic");
        return new ResponseEntity<>(ingredientService.deleteIngredient(ingredientId), HttpStatus.OK);
    }

}
