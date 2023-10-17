package com.sinter.cookspire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.service.RefreshTokenService;
import com.sinter.cookspire.service.UserService;
import com.sinter.cookspire.utils.JWTUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping(value = "/persist/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody UserDTO request) {
        logger.info("Entering persist user logic");
        return new ResponseEntity<>(userService.persistUser(request), HttpStatus.OK);
    }
    
    @PostMapping(value = "/fetch/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchUser(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering fetch user logic");
        return new ResponseEntity<>(userService.fetchUser(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering delete user logic");
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/follow/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> followUser(@RequestBody FollowerDTO request) {
        logger.info("Entering persist follower info");
        return new ResponseEntity<>(userService.persistFollower(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/followersInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllFollowerInfo(@PathParam(value = "userId") @Valid long userId) {
        logger.info("Entering fetch all follower info");
        return new ResponseEntity<>(userService.fetchAllFollowers(userId), HttpStatus.OK);
    }

}