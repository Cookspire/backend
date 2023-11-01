package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.entity.Follower;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.PostInteraction;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.FollowerRepository;
import com.sinter.cookspire.repository.PostInteractionRepository;
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
    FollowerRepository followerRepo;

    @Autowired
    MessageSource msgSrc;

    @Autowired
    PostInteractionRepository postInteractionRepo;

    Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostDTO persistPost(PostDTO request) {

        long like = 0L;
        long dislike = 0L;

        boolean hasLiked = false;
        boolean hasDisliked = false;

        Optional<Post> chkPost = postRepo.findById(request.getId());

        Post postEntity = new Post();

        if (request.getId() != 0 && chkPost.isPresent()) {
            postEntity.setCreatedOn(chkPost.get().getCreatedOn());
            postEntity.setId(chkPost.get().getId());

            like = postInteractionRepo.fetchLikes(postEntity.getId());
            dislike = postInteractionRepo.fetchDislikes(postEntity.getId());

            Optional<PostInteraction> chkLike = postInteractionRepo.findByPosts(postEntity);
            if (chkLike.isPresent()) {
                hasLiked = chkLike.get().isLikes();
                hasDisliked = chkLike.get().isDislikes();
            }

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

        postEntity.setUpdatedOn(LocalDateTime.now());

        long postId = postRepo.save(postEntity).getId();
        logger.info("Exit from persisting post.");
        return new PostDTO(postId, request.getContent(),
                new UserDTO(postEntity.getUsers().getId(), postEntity.getUsers().getUsername(),
                        postEntity.getUsers().getEmail(), postEntity.getUsers().getPassword(),
                        postEntity.getUsers().getCountry(),
                        postEntity.getUsers().isVerified(), postEntity.getUsers().getBio(),
                        postEntity.getUsers().getCreatedOn(), postEntity.getUsers().getUpdatedOn()),
                like,
                dislike, hasLiked, hasDisliked, postEntity.getCreatedOn(), postEntity.getUpdatedOn());
    }

    @Override
    public PostDTO fetchPost(@Valid Long postId) {
        Optional<Post> chkPost = postRepo.findById(postId);

        if (chkPost.isPresent()) {
            Post postEntity = chkPost.get();
            long like = 0L;
            long dislike = 0L;
            like = postInteractionRepo.fetchLikes(postEntity.getId());
            dislike = postInteractionRepo.fetchDislikes(postEntity.getId());
            boolean hasLiked = false;
            boolean hasDisliked = false;
            Optional<PostInteraction> chkLike = postInteractionRepo.findByUsers(chkPost.get().getUsers());
            if (chkLike.isPresent()) {
                hasLiked = chkLike.get().isLikes();
                hasDisliked = chkLike.get().isDislikes();
            }
            return new PostDTO(postId, postEntity.getContent(),
                    new UserDTO(postEntity.getUsers().getId(), postEntity.getUsers().getUsername(),
                            postEntity.getUsers().getEmail(), postEntity.getUsers().getPassword(),
                            postEntity.getUsers().getCountry(),
                            postEntity.getUsers().isVerified(), postEntity.getUsers().getBio(),
                            postEntity.getUsers().getCreatedOn(), postEntity.getUsers().getUpdatedOn()),
                    like,
                    dislike, hasLiked, hasDisliked, postEntity.getCreatedOn(), postEntity.getUpdatedOn());
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
            List<Post> postEntries = postRepo.findAllByUsersOrderByUpdatedOnDesc(chkUser.get());

            for (var postEntity : postEntries) {
                long like = postInteractionRepo.fetchLikes(postEntity.getId());
                long dislike = postInteractionRepo.fetchDislikes(postEntity.getId());

                boolean hasLiked = false;
                boolean hasDisliked = false;
                Optional<PostInteraction> chkLike = postInteractionRepo.findByUsers(chkUser.get());
                if (chkLike.isPresent()) {

                    hasLiked = chkLike.get().isLikes();
                    hasDisliked = chkLike.get().isDislikes();

                }

                response.add(new PostDTO(postEntity.getId(), postEntity.getContent(),
                        new UserDTO(postEntity.getUsers().getId(), postEntity.getUsers().getUsername(),
                                postEntity.getUsers().getEmail(), postEntity.getUsers().getPassword(),
                                postEntity.getUsers().getCountry(),
                                postEntity.getUsers().isVerified(), postEntity.getUsers().getBio(),
                                postEntity.getUsers().getCreatedOn(), postEntity.getUsers().getUpdatedOn()),
                        like,
                        dislike, hasLiked, hasDisliked, postEntity.getCreatedOn(), postEntity.getUpdatedOn()));

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

    @Override
    public List<PostDTO> fetchAllFollowersPost(Long userId) {

        Optional<Users> chkUser = userRepo.findById(userId);

        List<PostDTO> response = new ArrayList<PostDTO>();

        if (chkUser.isPresent()) {
            List<Follower> followers = followerRepo.findAllByFolloweeUsers(chkUser.get());

            for (var followerEntity : followers) {
                response.addAll(fetchAllPost(followerEntity.getId()));
            }

            response = response.stream().sorted((ob1, ob2) -> ob2.getUpdatedOn().compareTo(ob1.getUpdatedOn()))
                    .collect(Collectors.toList());
            return response;

        } else {
            logger.warn("User not found");
            logger.info("Exit from fetcing all followers posts.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<PostDTO> fetchAllTrendingPost(Long userId) {

        List<PostDTO> response = new ArrayList<PostDTO>();

        List<Users> verifiedUsers = userRepo.findAllByIsVerifiedTrue();

        for (var userEntity : verifiedUsers) {

            List<Post> posts = postRepo.findAllByUsers(userEntity);

            for (var postEntity : posts) {
                long like = postInteractionRepo.fetchLikes(postEntity.getId());
                long dislike = postInteractionRepo.fetchDislikes(postEntity.getId());

                boolean hasLiked = false;
                boolean hasDisliked = false;
                if (userId > 0) {
                    Optional<Users> chkUser = userRepo.findById(userId);
                    Optional<PostInteraction> chkLike = postInteractionRepo.findByUsers(chkUser.get());
                    if (chkLike.isPresent()) {

                        hasLiked = chkLike.get().isLikes();
                        hasDisliked = chkLike.get().isDislikes();

                    }
                }
                response.add(new PostDTO(postEntity.getId(), postEntity.getContent(),
                        new UserDTO(postEntity.getUsers().getId(), postEntity.getUsers().getUsername(),
                                postEntity.getUsers().getEmail(), postEntity.getUsers().getPassword(),
                                postEntity.getUsers().getCountry(),
                                postEntity.getUsers().isVerified(), postEntity.getUsers().getBio(),
                                postEntity.getUsers().getCreatedOn(), postEntity.getUsers().getUpdatedOn()),
                        like,
                        dislike, hasLiked, hasDisliked, postEntity.getCreatedOn(), postEntity.getUpdatedOn()));
            }

        }

        return response;

    }

}
