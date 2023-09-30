package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
