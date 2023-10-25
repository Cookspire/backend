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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.NotficationRequestDTO;
import com.sinter.cookspire.service.NotificationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin("*")
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource msgSrc;

    @PutMapping(value = "/persist/notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> persistNotification(@RequestBody NotficationRequestDTO request) {
        logger.info("Entering Persist notification class");
        return new ResponseEntity<>(notificationService.persistNotification(request), HttpStatus.OK);

    }

    @GetMapping(value = "/fetchAll/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllNotification(@PathParam(value = "userId") @Valid long userId) {
        logger.info("Entering fetch All notification class");
        return new ResponseEntity<>(notificationService.fetchAllNotification(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotification(@PathParam(value = "userId") @Valid long userId) {
        logger.info("Entering Delete notification class");
        return new ResponseEntity<>(notificationService.deleteNotification(userId), HttpStatus.OK);
    }

}
