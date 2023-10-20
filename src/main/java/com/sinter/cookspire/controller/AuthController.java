package com.sinter.cookspire.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinter.cookspire.dto.JWTResponseDTO;
import com.sinter.cookspire.dto.RefreshTokenDTO;
import com.sinter.cookspire.dto.RefreshTokenRequestDTO;
import com.sinter.cookspire.dto.VerifyUserDTO;
import com.sinter.cookspire.service.RefreshTokenService;
import com.sinter.cookspire.service.UserService;
import com.sinter.cookspire.utils.JWTUtils;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/verify/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDTO request) {
        logger.info("Entering verify user logic");
        return new ResponseEntity<>(userService.verifyUser(request), HttpStatus.OK);
    }

    @PostMapping(value = "/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        logger.info("Entering refresh token logic");

        RefreshTokenDTO response = refreshTokenService.verifyToken(request.getToken());

        String access_token = jwtUtils.createToken(response.getEmail());

        RefreshTokenDTO updrefresh = refreshTokenService.persistToken(new RefreshTokenDTO(response.getId(),
                UUID.randomUUID().toString(), response.getEmail(), response.getExpiryTime().plusHours(5)));

        return new ResponseEntity<>(new JWTResponseDTO(response.getEmail(), access_token, updrefresh.getToken()),
                HttpStatus.OK);
    }

}
