package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.FollowerResponseDTO;
import com.sinter.cookspire.dto.FollowersDataDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.dto.UserResponseDTO;
import com.sinter.cookspire.dto.VerifyUserDTO;
import com.sinter.cookspire.entity.Follower;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.FollowerRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    FollowerRepository followerRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDTO persistUser(UserDTO request) {

        Optional<Users> chkUser = userRepo.findById(request.getId());

        Users userEntity = new Users();

        if (request.getId() != 0 && chkUser.isPresent()) {
            userEntity.setCreatedOn(chkUser.get().getCreatedOn());
            userEntity.setId(chkUser.get().getId());
            if (chkUser.get().getEmail() != request.getEmail()) {
                Optional<Users> chkEmail = userRepo.findByEmail(request.getEmail());
                if (chkEmail.isPresent()) {
                    logger.error("Error Occured while persisting User Information.");
                    logger.info("Exit from Persisting User.");
                    throw new ApplicationException(msgSrc.getMessage("User.EmailExist", null, Locale.ENGLISH),
                            HttpStatus.BAD_REQUEST);
                }
            }
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

        userEntity.setBio(request.getBio());

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
                userEntity.getCountry(), userEntity.isVerified(), userEntity.getBio(), userEntity.getCreatedOn(),
                userEntity.getUpdatedOn());
    }

    @Override
    public UserDTO fetchUser(@Valid Long userId) {

        Optional<Users> chkUser = userRepo.findById(userId);

        if (chkUser.isPresent()) {
            Users userEntity = chkUser.get();
            return new UserDTO(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getCountry(), userEntity.isVerified(), userEntity.getBio(), userEntity.getCreatedOn(),
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

    @Override
    public FollowerDTO persistFollower(FollowerDTO request) {

        if (request.getFolloweeId() == request.getFollowerId()) {
            logger.warn("Function not allowed");
            logger.info("Exit from Persist Follow User.");
            throw new ApplicationException(msgSrc.getMessage("User.SelfFollow", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }
        Optional<Users> chkFollowerUser = userRepo.findById(request.getFollowerId());
        Optional<Users> chkFolloweeUser = userRepo.findById(request.getFolloweeId());

        if (chkFolloweeUser.isPresent() && chkFollowerUser.isPresent()) {
            Follower followerEntity = new Follower();
            Optional<Follower> chkFollower = followerRepo.findByFollowerUsersAndFolloweeUsers(chkFollowerUser.get(),
                    chkFolloweeUser.get());
            if (chkFollower.isEmpty()) {
                logger.warn("User not found");
                logger.info("Exit from Persist Follow User.");
                throw new ApplicationException(msgSrc.getMessage("User.NotFollowed", null, Locale.ENGLISH),
                        HttpStatus.NOT_FOUND);
            }
            if (request.isFollowUser()) {

                if (chkFollower.isPresent()) {
                    followerEntity.setId(chkFollower.get().getId());
                    followerEntity.setCreatedOn(chkFollower.get().getCreatedOn());
                }
                followerEntity.setFolloweeUsers(chkFollowerUser.get());
                followerEntity.setFollowerUsers(chkFolloweeUser.get());
                followerEntity.setUpdatedOn(LocalDateTime.now());

                followerRepo.save(followerEntity);

                return new FollowerDTO(followerEntity.getFolloweeUsers().getId(),
                        followerEntity.getFollowerUsers().getId(), request.isFollowUser());

            } else {
                followerRepo.deleteById(chkFollower.get().getId());
                return request;
            }

        } else {
            logger.warn("User not found");
            logger.info("Exit from Persist Follow User.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public FollowerResponseDTO fetchAllFollowers(@Valid long userId) {
        Optional<Users> chkUser = userRepo.findById(userId);

        FollowerResponseDTO response = new FollowerResponseDTO();

        List<FollowersDataDTO> followerList = new ArrayList<FollowersDataDTO>();

        List<FollowersDataDTO> followeeList = new ArrayList<FollowersDataDTO>();

        if (chkUser.isPresent()) {
            List<Follower> followers = followerRepo.findAllByFollowerUsers(chkUser.get());

            List<Follower> following = followerRepo.findAllByFolloweeUsers(chkUser.get());

            for (var followerEntity : followers) {
                followerList.add(new FollowersDataDTO(followerEntity.getFolloweeUsers().getUsername(),
                        followerEntity.getFolloweeUsers().isVerified()));
            }
            response.setFollowers(followerList);

            for (var followeeEntity : following) {
                followeeList.add(new FollowersDataDTO(followeeEntity.getFollowerUsers().getUsername(),
                        followeeEntity.getFollowerUsers().isVerified()));
            }
            response.setFollowing(followeeList);

            return response;

        } else {
            logger.warn("User not found");
            logger.info("Exit from fetcing all followers.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserResponseDTO verifyUser(VerifyUserDTO request) {

        Optional<Users> chkUser = userRepo.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (chkUser.isPresent()) {
            return new UserResponseDTO(chkUser.get().getId(), chkUser.get().getUsername(), chkUser.get().getEmail(),
                    chkUser.get().getCountry(), chkUser.get().isVerified(), chkUser.get().getBio(),
                    chkUser.get().getCreatedOn(), chkUser.get().getUpdatedOn());
        } else {
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.UNAUTHORIZED);
        }

    }

}
