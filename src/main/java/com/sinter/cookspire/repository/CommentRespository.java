package com.sinter.cookspire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Comment;
import com.sinter.cookspire.entity.Post;
@Transactional
public interface CommentRespository extends JpaRepository<Comment, Long>{

    List<Comment> findAllByPost(Post post);
    
}
