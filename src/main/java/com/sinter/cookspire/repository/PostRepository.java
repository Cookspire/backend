package com.sinter.cookspire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Users;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUsers(Users users);

    
}
