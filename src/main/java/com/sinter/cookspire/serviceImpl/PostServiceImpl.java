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

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.PostService;

import jakarta.validation.Valid;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostDTO persistPost(PostDTO request) {
        Optional<Post> chkPost = postRepo.findById(request.getId());

        Post postEntity = new Post();

        if (request.getId() != 0 && chkPost.isPresent()) {
            postEntity.setCreatedOn(chkPost.get().getCreatedOn());
            postEntity.setId(chkPost.get().getId());
        } else if (request.getId() == 0) {

            postEntity.setCreatedOn(LocalDateTime.now());

        } else {
            logger.error("Error occured while persisting post.");
            logger.info("Exit from persisting post.");
            throw new ApplicationException(msgSrc.getMessage("Post.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting post.");
            logger.info("Exit from persisting post.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            postEntity.setUsers(chkUser.get());

        postEntity.setContent(request.getContent());
        if (request.getLikes() == 0 && chkPost.isPresent())
            postEntity.setLikes(chkPost.get().getLikes());
        else
            postEntity.setLikes(request.getLikes());

        if (request.getDislikes() == 0 && chkPost.isPresent())
            postEntity.setDislikes(chkPost.get().getDislikes());
        else
            postEntity.setDislikes(request.getDislikes());

        postEntity.setUpdatedOn(LocalDateTime.now());

        long postId = postRepo.save(postEntity).getId();
        logger.info("Exit from persisting post.");
        return new PostDTO(postId, request.getContent(), request.getCreatedBy(), postEntity.getLikes(),
                postEntity.getDislikes(), postEntity.getCreatedOn(), postEntity.getUpdatedOn());
    }

    @Override
    public PostDTO fetchPost(@Valid Long postId) {
        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {
            Post postEntity = chkPost.get();
            return new PostDTO(postId, postEntity.getContent(), postEntity.getUsers().getId(),
                    postEntity.getLikes(),
                    postEntity.getDislikes(), postEntity.getCreatedOn(), postEntity.getUpdatedOn());
        }

        else {
            logger.warn("Post not found.");
            logger.info("Exit from fetching Post.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<PostDTO> fetchAllPost(@Valid Long userId) {
        List<PostDTO> response = new ArrayList<PostDTO>();
        Optional<Users> chkUser = userRepo.findById(userId);
        if (chkUser.isPresent()) {
            List<Post> postEntries = postRepo.findAllByUsers(chkUser.get());
            for (var postEntity : postEntries) {
                response.add(new PostDTO(postEntity.getId(), postEntity.getContent(), postEntity.getUsers().getId(),
                        postEntity.getLikes(),
                        postEntity.getDislikes(), postEntity.getCreatedOn(), postEntity.getUpdatedOn()));
            }
        } else {
            logger.warn("User not found.");
            logger.info("Exit from fetching All Posts.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
        logger.info("Exit from fetching All Posts.");
        return response;
    }

    @Override
    public ResponseDTO deletePost(@Valid Long postId) {
        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {
            postRepo.deleteById(postId);
            return new ResponseDTO("Post deleted Successfully");
        } else {
            logger.warn("Post not found");
            logger.info("Exit from deleting post.");
            throw new ApplicationException(msgSrc.getMessage("Post.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
