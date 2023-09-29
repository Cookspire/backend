package com.sinter.cookspire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping(value = "/persist/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody UserDTO request) {
        logger.info("Entering persist user logic");
        return new ResponseEntity<>(userService.persistUser(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchUser(@PathVariable(value = "userId") @Valid Long userId) {
        logger.info("Entering persist user logic");
        return new ResponseEntity<>(userService.fetchUser(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") @Valid Long userId) {
        logger.info("Entering persist user logic");
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

}