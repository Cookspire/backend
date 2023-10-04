package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Bookmark;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Users;
@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByUsers(Users users);

    Optional<Bookmark> findByPostsAndUsers(Post post, Users users);

}
