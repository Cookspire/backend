package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.NotficationRequestDTO;
import com.sinter.cookspire.dto.PostInteractionDTO;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.PostInteraction;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.PostInteractionRepository;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.NotificationService;
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

    @Autowired
    NotificationService notficationService;

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

        PostInteraction postInteractionEntity = new PostInteraction();

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null,
                    Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            postInteractionEntity.setUsers(chkUser.get());

        Optional<Post> chkPost = postRepo.findById(request.getPostId());
        if (chkPost.isEmpty()) {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            postInteractionEntity.setPosts(chkPost.get());

        if (request.getId() != 0 && chkInteraction.isPresent()) {
            postInteractionEntity.setCreatedOn(chkInteraction.get().getCreatedOn());
            postInteractionEntity.setId(chkInteraction.get().getId());
        } else if (request.getId() == 0) {
            postInteractionEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error occured while persisting post interaction.");
            logger.info("Exit from persisting post interaction.");
            throw new ApplicationException(msgSrc.getMessage("Post.Intr.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        postInteractionEntity.setUpdatedOn(LocalDateTime.now());

        if (request.isLiked()) {
            postInteractionEntity.setLikes(true);
            postInteractionEntity.setDislikes(false);
        } else {
            postInteractionEntity.setLikes(false);
            postInteractionEntity.setDislikes(true);
        }

        long postInteractionId = postInteractionRepo.save(postInteractionEntity).getId();

        // Add Async
        notficationService.persistNotification(
                new NotficationRequestDTO(0, request.getCreatedBy(), chkPost.get().getUsers().getId(), LocalDateTime.now(),
                        LocalDateTime.now(), msgSrc.getMessage("Notification.Like_MESSAGE", null, Locale.ENGLISH),
                        false));

        logger.info("Sending WEB SOCKET Message");

        List<NotficationRequestDTO> messages = notficationService.fetchAllNotification(postInteractionEntity.getUsers().getId());
        messagingTemplate.convertAndSendToUser(postInteractionEntity.getUsers().getEmail(), "/notification/postInteraction",
                messages);

        logger.info("Sent WEB SOCKET Message");

        logger.info("Exit from persisting post interaction.");

        return new PostInteractionDTO(postInteractionId, 0L, postInteractionEntity.getPosts().getId(),
                request.isLiked(), postInteractionEntity.getCreatedOn(), postInteractionEntity.getUpdatedOn());
    }

}
