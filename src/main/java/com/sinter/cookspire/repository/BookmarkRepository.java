package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Bookmark;
@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
