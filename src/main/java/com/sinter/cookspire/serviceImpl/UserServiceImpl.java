package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.EncryptorDTO;
import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.FollowerResponseDTO;
import com.sinter.cookspire.dto.FollowersDataDTO;
import com.sinter.cookspire.dto.ImageRequestDTO;
import com.sinter.cookspire.dto.JWTResponseDTO;
import com.sinter.cookspire.dto.RefreshTokenDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.dto.UserGeneralAnalysisDTO;
import com.sinter.cookspire.dto.VerifyUserDTO;
import com.sinter.cookspire.entity.Follower;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.FollowerRepository;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.RefreshTokenService;
import com.sinter.cookspire.service.UserService;
import com.sinter.cookspire.utils.Decryptor;
import com.sinter.cookspire.utils.Encryptor;
import com.sinter.cookspire.utils.JWTUtils;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    FollowerRepository followerRepo;

    @Autowired
    MessageSource msgSrc;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    Encryptor encryptor;

    @Autowired
    Decryptor decryptor;

    @Autowired
    JWTUtils jwtService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDTO persistUser(UserDTO request) {

        Optional<Users> chkUser = userRepo.findById(request.getId());

        Users userEntity = new Users();

        if (request.getId() != 0 && chkUser.isPresent()) {
            userEntity.setCreatedOn(chkUser.get().getCreatedOn());
            userEntity.setId(chkUser.get().getId());
            if (!chkUser.get().getEmail().equals(request.getEmail())) {
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

        if (request.getOldPassword() != null) {

            if (chkUser.isPresent()) {
                boolean passwordChecker = decryptor.isSame(chkUser.get().getPassword(), request.getOldPassword(),
                        chkUser.get().getSalt());
                if (passwordChecker) {
                    EncryptorDTO encryptData = encryptor.encryptor(request.getPassword());
                    userEntity.setPassword(encryptData.getHashText());
                    userEntity.setSalt(encryptData.getSalt());
                } else {
                    logger.error("Error Occured while changing user password.");
                    logger.info("Exit from Persisting User.");
                    throw new ApplicationException(msgSrc.getMessage("User.IncorrectPassowrd", null, Locale.ENGLISH),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                logger.error("Error Occured while persisting User Information.");
                logger.info("Exit from Persisting User.");
                throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            }
        } else if (chkUser.isEmpty()) {
            EncryptorDTO encryptData = encryptor.encryptor(request.getPassword());
            userEntity.setPassword(encryptData.getHashText());
            userEntity.setSalt(encryptData.getSalt());
        }
        logger.info("After encryption");
        userEntity.setEmail(request.getEmail());
        userEntity.setCountry(request.getCountry());
        userEntity.setVerified(request.getIsVerified());
        userEntity.setUpdatedOn(LocalDateTime.now());
        logger.info("After setting user details");
        long userId = userRepo.save(userEntity).getId();
        logger.info("After setting user details");
        logger.info("Exit from Persisting User.");
        return new UserDTO(userId, userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(),
                userEntity.getCountry(), userEntity.isVerified(), userEntity.getBio(), userEntity.getCreatedOn(),
                userEntity.getUpdatedOn());
    }

    @Override
    public UserDTO fetchUser(@Valid String email) {

        Optional<Users> chkUser = userRepo.findByEmail(email);

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
            Optional<Follower> chkFollower = followerRepo.findByFollowerUsersAndFolloweeUsers(chkFolloweeUser.get(),
                    chkFollowerUser.get());

            System.out.println(chkFollower.isPresent());

            if (chkFollower.isPresent()) {
                logger.warn("User not found");
                logger.info("Exit from Persist Follow User.");
                throw new ApplicationException(msgSrc.getMessage("User.Followed", null, Locale.ENGLISH),
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
                if (chkFollower.isEmpty()) {
                    logger.warn("User not found");
                    logger.info("Exit from Persist Follow User.");
                    throw new ApplicationException(msgSrc.getMessage("User.NotFollowed", null, Locale.ENGLISH),
                            HttpStatus.NOT_FOUND);
                }
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
                followerList.add(new FollowersDataDTO(followerEntity.getFolloweeUsers().getId(),
                        followerEntity.getFolloweeUsers().getUsername(),
                        followerEntity.getFolloweeUsers().isVerified()));
            }
            response.setFollowers(followerList);

            for (var followeeEntity : following) {
                followeeList.add(new FollowersDataDTO(followeeEntity.getFollowerUsers().getId(),
                        followeeEntity.getFollowerUsers().getUsername(),
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
    public JWTResponseDTO verifyUser(VerifyUserDTO request) {

        Optional<Users> chkUser = userRepo.findByEmail(request.getEmail());

        if (chkUser.isPresent()) {

            boolean passwordChecker = decryptor.isSame(chkUser.get().getPassword(), request.getPassword(),
                    chkUser.get().getSalt());

            if (!passwordChecker) {
                logger.error("Error Occured while changing user password.");
                logger.info("Exit from Persisting User.");
                throw new ApplicationException(msgSrc.getMessage("User.IncorrectPassowrd", null, Locale.ENGLISH),
                        HttpStatus.BAD_REQUEST);
            }

            String access_token = jwtService.createToken(chkUser.get().getEmail());
            String refresh = refreshTokenService
                    .persistToken(new RefreshTokenDTO(0, UUID.randomUUID().toString(), chkUser.get().getEmail(),
                            LocalDateTime.now().plusHours(5)))
                    .getToken();
            return new JWTResponseDTO(chkUser.get().getEmail(), access_token, refresh);
        } else {
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public UserGeneralAnalysisDTO fetchGeneralUserAnalysis(@Valid long userId) {

        Optional<Users> chkUser = userRepo.findById(userId);

        UserGeneralAnalysisDTO response = new UserGeneralAnalysisDTO();

        if (chkUser.isPresent()) {
            response.setFollowerCount(followerRepo.countUserFollowers(userId));

            response.setFollowingCount(followerRepo.countUserFollowing(userId));

            response.setPostCount(postRepo.findAllByUsers(chkUser.get()).size());

            return response;

        } else {
            logger.warn("User not found");
            logger.info("Exit from fetcing all followers.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public UserDTO uploadProfilePicture(ImageRequestDTO imageDetails) {
        Optional<Users> chkUser = userRepo.findById(imageDetails.getId());

        if (chkUser.isPresent()) {
            String imageName = imageDetails.getName() == null ? "IMG_" + chkUser.get().getId() : imageDetails.getName();
            chkUser.get().setImageName(imageName);
            chkUser.get().setImageType(imageDetails.getType());
            chkUser.get().setImageData(imageDetails.getImageData());

            userRepo.save(chkUser.get());
        } else {
            logger.warn("User not found");
            logger.info("Exit from fetcing all followers.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
        return new UserDTO(chkUser.get().getId(), chkUser.get().getUsername(), chkUser.get().getEmail(),
                chkUser.get().getCountry(), chkUser.get().isVerified(), chkUser.get().getBio(),
                chkUser.get().getCreatedOn(), chkUser.get().getUpdatedOn(), chkUser.get().getImageName(),
                chkUser.get().getImageType(), chkUser.get().getImageData());
    }

}
