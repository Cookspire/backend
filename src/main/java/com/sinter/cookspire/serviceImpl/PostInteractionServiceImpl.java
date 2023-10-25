package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.PostInteractionDTO;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.PostInteraction;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.PostInteractionRepository;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.PostInteractionService;

@Service
public class PostInteractionServiceImpl implements PostInteractionService {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    PostInteractionRepository postInteractionRepo;

    Logger logger = LoggerFactory.getLogger(PostInteractionServiceImpl.class);

    @Override
    public PostInteractionDTO persistInteraction(PostInteractionDTO request) {

        Optional<PostInteraction> chkInteraction = postInteractionRepo.findById(request.getId());

        if (request.getId() == 0) {
            Optional<PostInteraction> chkUser = postInteractionRepo.findByUserAndPost(request.getCreatedBy(),
                    request.getPostId());
            if (chkUser.isPresent()) {
                logger.error("Error occured while persisting post interaction.");
                logger.info("Exit from persisting post interaction.");
                throw new ApplicationException(msgSrc.getMessage("Post.Intr.Duplicate", null,
                        Locale.ENGLISH),
                        HttpStatus.NOT_FOUND);
            }
        }

        PostInteraction postEntity = new PostInteraction();

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null,
                    Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            postEntity.setUsers(chkUser.get());

        Optional<Post> chkPost = postRepo.findById(request.getPostId());
        if (chkPost.isEmpty()) {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            postEntity.setPosts(chkPost.get());

        if (request.getId() != 0 && chkInteraction.isPresent()) {
            postEntity.setCreatedOn(chkInteraction.get().getCreatedOn());
            postEntity.setId(chkInteraction.get().getId());
        } else if (request.getId() == 0) {
            postEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("Post.Intr.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        postEntity.setUpdatedOn(LocalDateTime.now());

        if (request.isLiked()) {
            postEntity.setLikes(true);
            postEntity.setDislikes(false);
        } else {
            postEntity.setLikes(false);
            postEntity.setDislikes(true);
        }

        long postInteractionId = postInteractionRepo.save(postEntity).getId();

        logger.info("Exit from persisting post interaction.");

        // Add Async

        return new PostInteractionDTO(postInteractionId, 0L, postEntity.getPosts().getId(),
                request.isLiked(), postEntity.getCreatedOn(), postEntity.getUpdatedOn());
    }

}
