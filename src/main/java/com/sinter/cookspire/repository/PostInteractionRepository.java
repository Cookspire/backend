package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.PostInteraction;
import com.sinter.cookspire.entity.Users;

@Repository
public interface PostInteractionRepository extends JpaRepository<PostInteraction, Long> {

    Optional<PostInteraction> findByPosts(Post post);

    @Query(nativeQuery = true, value = "Select count(*) from post_interaction where post_id =:postId and likes=true")
    Long fetchLikes(@Param(value = "postId") long postId);

    @Query(nativeQuery = true, value = "Select count(*) from post_interaction where post_id =:postId and dislikes=true")
    Long fetchDislikes(@Param(value = "postId") long postId);

    @Query(nativeQuery = true, value = "Select * from post_interaction where post_id =:postId and user_id=:createdBy limit 1")
    Optional<PostInteraction> findByUserAndPost(@Param(value = "createdBy") long createdBy,
            @Param(value = "postId") long postId);

    Optional<PostInteraction> findByUsers(Users users);

    List<PostInteraction> findAll();

    Optional<PostInteraction> findByUsersAndPosts(Users users, Post postEntity);

}
