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

import com.sinter.cookspire.dto.RepliesDTO;
import com.sinter.cookspire.service.RepliesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class RepliesController {
    @Autowired
    RepliesService repliesService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/replies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistReplies(@RequestBody RepliesDTO request) {
        logger.info("Entering persist replies logic");
        return new ResponseEntity<>(repliesService.persistReplies(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/replies/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllReplies(@PathParam(value = "commentId") @Valid Long commentId) {
        logger.info("Entering fetchAll replies logic");
        return new ResponseEntity<>(repliesService.fetchAllReplies(commentId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/replies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteReplies(@PathParam(value = "repliesId") @Valid Long repliesId) {
        logger.info("Entering delete replies logic");
        return new ResponseEntity<>(repliesService.deleteReplies(repliesId), HttpStatus.OK);
    }

}
