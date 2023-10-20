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

import com.sinter.cookspire.dto.BookmarkDTO;
import com.sinter.cookspire.service.BookmarkService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class BookmarkController {

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PutMapping(value = "/persist/bookmark", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistUser(@RequestBody BookmarkDTO request) {
        logger.info("Entering persist bookmark logic");
        return new ResponseEntity<>(bookmarkService.persistBookmark(request), HttpStatus.OK);
    }

    @PostMapping(value = "/fetchAll/bookmark/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetcAllBookmark(@PathParam(value = "userId") @Valid Long userId) {
        logger.info("Entering fetchAll bookmark logic");
        return new ResponseEntity<>(bookmarkService.fetchAllBookmark(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/bookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBookmark(@PathParam(value = "bookmarkId") @Valid Long bookmarkId) {
        logger.info("Entering delete bookmark logic");
        return new ResponseEntity<>(bookmarkService.deleteBookmark(bookmarkId), HttpStatus.OK);
    }

}
