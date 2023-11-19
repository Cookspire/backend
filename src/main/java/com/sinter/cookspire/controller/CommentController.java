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

import com.sinter.cookspire.dto.CommentDTO;
import com.sinter.cookspire.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/comment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistComment(@RequestBody CommentDTO request) {
        logger.info("Entering persist comment logic");
        return new ResponseEntity<>(commentService.persistComment(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/comment/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllCommentByPost(@PathParam(value = "postId") @Valid Long postId) {
        logger.info("Entering fetchAll comment by posts logic");
        return new ResponseEntity<>(commentService.fetchAllCommentByPost(postId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteComment(@PathParam(value = "commentId") @Valid Long commentId) {
        logger.info("Entering delete comment logic");
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.OK);
    }
}
