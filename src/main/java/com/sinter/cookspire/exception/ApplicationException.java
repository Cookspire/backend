package com.sinter.cookspire.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private String message;

    private HttpStatus httpStatus;

}
