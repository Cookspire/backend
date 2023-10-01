package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Comment;

public interface CommentRespository extends JpaRepository<Comment, Long>{
    
}
