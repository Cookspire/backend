package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
