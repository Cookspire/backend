package com.sinter.cookspire.exception;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sinter.cookspire.dto.ApiResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<?> handleException(final ApplicationException exception, final HttpServletRequest request) {

        var guid = UUID.randomUUID().toString();

        logger.error(msgSrc.getMessage("Exception.info", null, Locale.ENGLISH) + guid);

        var response = new ApiResponseDTO(guid, exception.getHttpStatus().value(), exception.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(response, exception.getHttpStatus());

    }

}
