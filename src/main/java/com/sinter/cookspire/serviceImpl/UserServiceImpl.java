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

import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDTO persistUser(UserDTO request) {

        Optional<Users> chkUser = userRepo.findById(request.getId());

        Users userEntity = new Users();

        if (request.getId() != 0 && chkUser.isPresent()) {
            logger.info("Inside If");
            userEntity.setCreatedOn(chkUser.get().getCreatedOn());
            userEntity.setId(chkUser.get().getId());
        } else if (request.getId() == 0) {
            Optional<Users> chkEmail = userRepo.findByEmail(request.getEmail());
            if (chkEmail.isPresent()) {
                logger.error("Error Occured while persisting User Information.");
                logger.info("Exit from Persisting User.");
                throw new ApplicationException(msgSrc.getMessage("User.EmailExist", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            } else
                userEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error Occured while persisting User Information.");
            logger.info("Exit from Persisting User.");
            throw new ApplicationException(msgSrc.getMessage("User.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setEmail(request.getEmail());
        // add hashing here
        userEntity.setSalt("qwueyasduhk12312uqkeqjwe");
        userEntity.setCountry(request.getCountry());
        userEntity.setVerified(request.getIsVerified());
        userEntity.setUpdatedOn(LocalDateTime.now());

        long userId = userRepo.save(userEntity).getId();
        logger.info("Exit from Persisting User.");
        return new UserDTO(userId, userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(),
                userEntity.getCountry(), userEntity.isVerified(), userEntity.getCreatedOn(), userEntity.getUpdatedOn());
    }

    @Override
    public UserDTO fetchUser(@Valid Long userId) {

        Optional<Users> chkUser = userRepo.findById(userId);

        if (chkUser.isPresent()) {
            Users userEntity = chkUser.get();
            return new UserDTO(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getCountry(), userEntity.isVerified(), userEntity.getCreatedOn(),
                    userEntity.getUpdatedOn());
        }

        else {
            logger.warn("User not found");
            logger.info("Exit from fetching User.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseDTO deleteUser(@Valid Long userId) {
        Optional<Users> chkUser = userRepo.findById(userId);

        if (chkUser.isPresent()) {
            userRepo.deleteById(userId);
            return new ResponseDTO("User deleted Successfully");
        } else {
            logger.warn("User not found");
            logger.info("Exit from Deleting User.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
