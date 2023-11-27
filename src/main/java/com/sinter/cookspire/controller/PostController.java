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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.service.PostService;
import com.sinter.cookspire.utils.ImageSignatureValidator;

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

    @Autowired
    ImageSignatureValidator imageSign;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistPost(@RequestPart(value = "data") @Valid PostDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        if (null != file) {
            try {
                logger.info("Entering persist post with image logic");
                if (imageSign.processImageFormat(file.getInputStream())
                        && (file.getOriginalFilename() != null
                                && (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE))
                                ||
                                file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE))) {

                    request.setImageData(file.getBytes());
                    request.setImageType(file.getContentType());
                    request.setImageName(file.getOriginalFilename());
                    return new ResponseEntity<>(postService.persistPost(request), HttpStatus.OK);

                } else {
                    logger.error("Invalid Signature for post with image.");
                    logger.error("Exiting from post with image logic.");
                    throw new ApplicationException(msgSrc.getMessage("User.Error", null, Locale.ENGLISH),
                            HttpStatus.BAD_REQUEST);
                }
            } catch (NoSuchMessageException | IOException e) {
                logger.error("Invalid Signature for post with image.");
                logger.error("Exiting from post with image logic.");
                throw new ApplicationException(msgSrc.getMessage("User.Error", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("Entering persist post logic without image");
            return new ResponseEntity<>(postService.persistPost(request), HttpStatus.OK);
        }

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

    @PostMapping(value = "/fetchAll/post/user/follower", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllFollowersPost(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering fetchAll followers post logic");
        return new ResponseEntity<>(postService.fetchAllFollowersPost(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/trending/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllTrendingPost(
            @RequestParam(value = "userId", required = false) Long userId) {
        logger.info("Entering fetchAll trending post logic");

        return new ResponseEntity<>(postService.fetchAllTrendingPost(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePost(@PathParam(value = "postId") @Valid Long postId) {
        logger.info("Entering delete post logic");
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }

}