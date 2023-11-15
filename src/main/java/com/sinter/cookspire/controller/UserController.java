package com.sinter.cookspire.controller;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.ImageUploadDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.service.RefreshTokenService;
import com.sinter.cookspire.service.UserService;
import com.sinter.cookspire.utils.ImageSignatureValidator;
import com.sinter.cookspire.utils.JWTUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    MessageSource msgSrc;

    @Autowired
    ImageSignatureValidator imageSign;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping(value = "/persist/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody UserDTO request) {
        logger.info("Entering persist user logic");
        return new ResponseEntity<>(userService.persistUser(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchUser(@RequestParam(value = "email") @Valid String email) {
        logger.info("Entering fetch user logic");
        return new ResponseEntity<>(userService.fetchUser(email), HttpStatus.OK);
    }

    @PatchMapping(value = "/upload/profile/picture", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> uploadProfilePicture(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") String request) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.readValue(request, ImageUploadDTO.class);

            if (imageSign.isValigJpeg(file.getInputStream()) ||
                    imageSign.isValigPng(file.getInputStream())) {

                System.out.println(file.getOriginalFilename());

            } else
                return new ResponseEntity<>(new ResponseDTO(msgSrc.getMessage("Format.INVALID", null, Locale.ENGLISH)),
                        HttpStatus.BAD_REQUEST);

        } catch (JsonProcessingException e) {
            throw new ApplicationException(msgSrc.getMessage("ObjectMapper.INVALID", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        } catch (NoSuchMessageException e) {
            throw new ApplicationException(msgSrc.getMessage("Format.INVALID", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new ApplicationException(msgSrc.getMessage("Format.INVALID", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        return null;
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

    @PostMapping(value = "/fetch/general/userAnalysis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchGeneralUserAnalysis(@PathParam(value = "userId") @Valid long userId) {
        logger.info("Entering fetch all user analysis");
        return new ResponseEntity<>(userService.fetchGeneralUserAnalysis(userId), HttpStatus.OK);
    }

}