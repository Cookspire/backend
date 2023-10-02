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

import com.sinter.cookspire.dto.CommentDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Comment;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.CommentRespository;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.CommentService;

import jakarta.validation.Valid;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepository postRepo;

    @Autowired
    CommentRespository commentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentDTO persistComment(CommentDTO request) {
        Optional<Comment> chkComment = commentRepo.findById(request.getId());

        Comment commentEntity = new Comment();

        if (request.getId() != 0 && chkComment.isPresent()) {
            commentEntity.setCreatedOn(chkComment.get().getCreatedOn());
            commentEntity.setId(chkComment.get().getId());
        } else if (request.getId() == 0) {
            commentEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error occured while persisting comment.");
            logger.info("Exit from persisting comment.");
            throw new ApplicationException(msgSrc.getMessage("Comment.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting post.");
            logger.info("Exit from persisting post.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            commentEntity.setUsers(chkUser.get());

        commentEntity.setContent(request.getContent());
        if (request.getLikes() == 0 && chkComment.isPresent())
            commentEntity.setLikes(chkComment.get().getLikes());
        else
            commentEntity.setLikes(request.getLikes());

        if (request.getDislikes() == 0 && chkComment.isPresent())
            commentEntity.setDislikes(chkComment.get().getDislikes());
        else
            commentEntity.setDislikes(request.getDislikes());

        commentEntity.setUpdatedOn(LocalDateTime.now());

        long commentId = commentRepo.save(commentEntity).getId();
        logger.info("Exit from persisting comment.");
        return new CommentDTO(commentId, commentEntity.getUsers().getId(), commentEntity.getContent(),
                commentEntity.getLikes(), commentEntity.getDislikes(), commentEntity.getCreatedOn(),
                commentEntity.getUpdatedOn());
    }

    @Override
    public List<CommentDTO> fetchAllCommentByPost(@Valid Long postId) {

        List<CommentDTO> response = new ArrayList<CommentDTO>();

        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {

            List<Comment> comments = commentRepo.findAllByPosts(chkPost.get());
            for (var comment : comments) {
                response.add(new CommentDTO(comment.getId(), comment.getUsers().getId(), comment.getContent(),
                        comment.getLikes(), comment.getDislikes(), comment.getCreatedOn(),
                        comment.getUpdatedOn()));
            }

        } else {
            logger.warn("Post not found");
            logger.info("Exit from fetching post.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        return response;

    }

    @Override
    public ResponseDTO deleteComment(@Valid Long commentId) {

        Optional<Comment> chkComment = commentRepo.findById(commentId);

        if (chkComment.isPresent()) {
            commentRepo.deleteById(commentId);
            return new ResponseDTO("Comment deleted Successfully");
        } else {
            logger.warn("Comment not found");
            logger.info("Exit from deleting comment.");
            throw new ApplicationException(msgSrc.getMessage("Comment.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
