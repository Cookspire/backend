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

import com.sinter.cookspire.dto.RepliesDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Comment;
import com.sinter.cookspire.entity.Replies;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.CommentRespository;
import com.sinter.cookspire.repository.RepliesRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.RepliesService;

import jakarta.validation.Valid;

@Service
public class RepliesServiceImpl implements RepliesService {

    @Autowired
    RepliesRepository repliesRepo;

    @Autowired
    CommentRespository commentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);

    @Override
    public RepliesDTO persistReplies(RepliesDTO request) {

        Optional<Replies> chkReplies = repliesRepo.findById(request.getId());

        Replies repliesEntity = new Replies();

        if (request.getId() != 0 && chkReplies.isPresent()) {
            repliesEntity.setCreatedOn(chkReplies.get().getCreatedOn());
            repliesEntity.setId(chkReplies.get().getId());
        } else if (request.getId() == 0) {
            repliesEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error occured while persisting replies.");
            logger.info("Exit from persisting replies.");
            throw new ApplicationException(msgSrc.getMessage("Replies.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting replies.");
            logger.info("Exit from persisting replies.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            repliesEntity.setUsers(chkUser.get());

        repliesEntity.setContent(request.getContent());
        if (request.getLikes() == 0 && chkReplies.isPresent())
            repliesEntity.setLikes(chkReplies.get().getLikes());
        else
            repliesEntity.setLikes(request.getLikes());

        if (request.getDislikes() == 0 && chkReplies.isPresent())
            repliesEntity.setDislikes(chkReplies.get().getDislikes());
        else
            repliesEntity.setDislikes(request.getDislikes());

        repliesEntity.setUpdatedOn(LocalDateTime.now());

        long repliesId = repliesRepo.save(repliesEntity).getId();
        logger.info("Exit from persisting replies.");
        return new RepliesDTO(repliesId, repliesEntity.getUsers().getId(), repliesEntity.getContent(),
                repliesEntity.getComments().getId(),
                repliesEntity.getLikes(), repliesEntity.getDislikes(), repliesEntity.getCreatedOn(),
                repliesEntity.getUpdatedOn());
    }

    @Override
    public List<RepliesDTO> fetchAllReplies(@Valid Long commentId) {
        List<RepliesDTO> response = new ArrayList<RepliesDTO>();

        Optional<Comment> chkComment = commentRepo.findById(commentId);

        if (chkComment.isPresent()) {

            List<Replies> replies = repliesRepo.findAllByComments(chkComment.get());
            for (var reply : replies) {
                response.add(new RepliesDTO(reply.getId(), reply.getUsers().getId(), reply.getContent(),
                        reply.getComments().getId(),
                        reply.getLikes(), reply.getDislikes(), reply.getCreatedOn(),
                        reply.getUpdatedOn()));
            }

        } else {
            logger.warn("Comment not found");
            logger.info("Exit from fetching comment.");
            throw new ApplicationException(msgSrc.getMessage("Replies.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @Override
    public ResponseDTO deleteReplies(@Valid Long repliesId) {

        Optional<Replies> chkReplies = repliesRepo.findById(repliesId);

        if (chkReplies.isPresent()) {
            repliesRepo.deleteById(repliesId);
            return new ResponseDTO("Replies deleted Successfully");
        } else {
            logger.warn("Replies not found");
            logger.info("Exit from deleting replies.");
            throw new ApplicationException(msgSrc.getMessage("Replies.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
