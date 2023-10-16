package com.sinter.cookspire.utils;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sinter.cookspire.exception.ApplicationException;

@Service
public class JWTUtils {

    @Autowired
    MessageSource msgSrc;

    public String createToken(String userData) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(msgSrc.getMessage("JWT.SECRET", null, Locale.ENGLISH));

            String token = JWT.create().withIssuer(msgSrc.getMessage("App.Name", null, Locale.ENGLISH))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 180))
                    .withClaim("email", userData)
                    .sign(algorithm);
            System.out.println("Encoded data::" + token);
            return token;
        } catch (JWTCreationException e) {
            throw new ApplicationException(msgSrc.getMessage("Refresh.Exception", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    public boolean decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(msgSrc.getMessage("JWT.SECRET", null, Locale.ENGLISH));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(msgSrc.getMessage("App.Name", null, Locale.ENGLISH)).build();
            DecodedJWT decodedData = verifier.verify(token);
            String payload = decodedData.getClaim("email").toString();
            System.out.println("decoded data::" + payload);
            return true;
        } catch (JWTVerificationException e) {
            throw new ApplicationException(msgSrc.getMessage("Refresh.Expired", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
