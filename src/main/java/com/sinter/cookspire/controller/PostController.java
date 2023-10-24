package com.sinter.cookspire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.service.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody PostDTO request) {
        logger.info("Entering persist post logic");
        return new ResponseEntity<>(postService.persistPost(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetch/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchPost(@PathParam(value = "postId") @Valid Long postId) {
        logger.info("Entering fetch post logic");
        return new ResponseEntity<>(postService.fetchPost(postId), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/post/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllPost(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering fetchAll post logic");
        return new ResponseEntity<>(postService.fetchAllPost(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePost(@PathParam(value = "postId") @Valid Long postId) {
        logger.info("Entering delete post logic");
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }

}