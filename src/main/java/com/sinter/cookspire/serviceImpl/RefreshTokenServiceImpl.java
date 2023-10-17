package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RefreshTokenDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.RefreshToken;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.RefreshTokenRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageSource msgSrc;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public RefreshTokenDTO persistToken(RefreshTokenDTO request) {

        logger.info("Entering persisting refresh token service.");

        Optional<Users> chkUser = userRepository.findByEmail(request.getEmail());

        if (chkUser.isPresent()) {

            Optional<RefreshToken> chkToken = refreshTokenRepository.findById(request.getId());
            RefreshToken tokenEntity = new RefreshToken();
            if (chkToken.isPresent()) {
                tokenEntity.setId(request.getId());
            }
            tokenEntity.setToken(request.getToken());
            tokenEntity.setExpiry(request.getExpiryTime());
            tokenEntity.setUsers(chkUser.get());
            long tokenId = refreshTokenRepository.save(tokenEntity).getId();

            logger.info("Exit from persisting refresh token service.");
            return new RefreshTokenDTO(tokenId, tokenEntity.getToken(), tokenEntity.getUsers().getEmail(),
                    tokenEntity.getExpiry());

        } else {
            logger.info("Exception occured in persisting refresh token service.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public RefreshTokenDTO getToken(String token) {

        logger.info("Entering fetch refresh token service.");
        Optional<RefreshToken> chkToken = refreshTokenRepository.findByToken(token);
        if (chkToken.isPresent()) {
            logger.info("Exit from fetch refresh token service.");
            return new RefreshTokenDTO(chkToken.get().getId(), chkToken.get().getToken(),
                    chkToken.get().getUsers().getEmail(),
                    chkToken.get().getExpiry());
        } else {
            logger.info("Exception during fetch refresh token service.");
            throw new ApplicationException(msgSrc.getMessage("Refresh.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public RefreshTokenDTO verifyToken(String token) {
        logger.info("Entering verify refresh token service.");
        Optional<RefreshToken> chkToken = refreshTokenRepository.findByToken(token);
        if (chkToken.isPresent()) {

            LocalDateTime expiry = chkToken.get().getExpiry();

            if (LocalDateTime.now().isBefore(expiry)) {
                logger.info("Exit from verify refresh token service.");
                return new RefreshTokenDTO(chkToken.get().getId(), chkToken.get().getToken(),
                        chkToken.get().getUsers().getEmail(),
                        chkToken.get().getExpiry());
            } else {
                logger.info("Exception during verify refresh token service.");
                throw new ApplicationException(msgSrc.getMessage("Refresh.NotFound", null, Locale.ENGLISH),
                        HttpStatus.FORBIDDEN);
            }

        } else {
            logger.info("Exception during verify refresh token service.");
            throw new ApplicationException(msgSrc.getMessage("Refresh.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseDTO deleteToken(String token) {
        logger.info("Entering delete refresh token service.");
        Optional<RefreshToken> chkToken = refreshTokenRepository.findByToken(token);
        if (chkToken.isPresent()) {
            refreshTokenRepository.delete(chkToken.get());
            logger.info("Exit from delete refresh token service.");
            return new ResponseDTO(msgSrc.getMessage("Refresh.Delete", null, Locale.ENGLISH));
        } else {
            logger.info("Exception during delete refresh token service.");
            throw new ApplicationException(msgSrc.getMessage("Refresh.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
