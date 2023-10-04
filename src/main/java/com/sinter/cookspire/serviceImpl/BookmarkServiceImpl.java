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

import com.sinter.cookspire.dto.BookmarkDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Bookmark;
import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.BookmarkRepository;
import com.sinter.cookspire.repository.PostRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.BookmarkService;

import jakarta.validation.Valid;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    BookmarkRepository bookmarkRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    MessageSource msgSrc;

    Logger logger = LoggerFactory.getLogger(BookmarkServiceImpl.class);

    @Override
    public BookmarkDTO persistBookmark(BookmarkDTO request) {
        Optional<Bookmark> chkBookmark = bookmarkRepo.findById(request.getId());

        Bookmark bookmarkEntity = new Bookmark();

        if (request.getId() != 0 && chkBookmark.isPresent()) {
            bookmarkEntity.setCreatedOn(chkBookmark.get().getCreatedOn());
            bookmarkEntity.setId(chkBookmark.get().getId());
        } else if (request.getId() == 0) {
            bookmarkEntity.setCreatedOn(LocalDateTime.now());
        } else {
            logger.error("Error occured while persisting bookmark.");
            logger.info("Exit from persisting bookmark.");
            throw new ApplicationException(msgSrc.getMessage("Bookmark.Error", null, Locale.ENGLISH),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Users> chkUser = userRepo.findById(request.getCreatedBy());
        if (chkUser.isEmpty()) {
            logger.error("Error occured while persisting bookmark.");
            logger.info("Exit from persisting bookmark.");
            throw new ApplicationException(msgSrc.getMessage("Bookmark.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            bookmarkEntity.setUsers(chkUser.get());

        Optional<Post> chkPost = postRepo.findById(request.getPostId());
    
        if (chkPost.isEmpty() || bookmarkRepo.findByPostsAndUsers(chkPost.get(), chkUser.get()).isPresent()) {
            logger.error("Error occured while persisting bookmark.");
            logger.info("Exit from persisting bookmark.");
            throw new ApplicationException(msgSrc.getMessage("Bookmark.Error", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        } else
            bookmarkEntity.setPosts(chkPost.get());

        bookmarkEntity.setUpdatedOn(LocalDateTime.now());

        long bookmarkId = bookmarkRepo.save(bookmarkEntity).getId();
        logger.info("Exit from persisting bookmark.");
        return new BookmarkDTO(bookmarkId, bookmarkEntity.getPosts().getId(), bookmarkEntity.getUsers().getId(),
                bookmarkEntity.getCreatedOn(), bookmarkEntity.getUpdatedOn());
    }

    @Override
    public List<BookmarkDTO> fetchAllBookmark(@Valid Long userId) {
        List<BookmarkDTO> response = new ArrayList<BookmarkDTO>();

        Optional<Users> chkUser = userRepo.findById(userId);

        if (chkUser.isPresent()) {

            List<Bookmark> bookmarks = bookmarkRepo.findAllByUsers(chkUser.get());
            for (var bookmark : bookmarks) {
                response.add(new BookmarkDTO(bookmark.getId(), bookmark.getPosts().getId(), bookmark.getUsers().getId(),
                        bookmark.getCreatedOn(), bookmark.getUpdatedOn()));
            }

        } else {
            logger.warn("User not found.");
            logger.info("Exit from fetching user.");
            throw new ApplicationException(msgSrc.getMessage("User.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @Override
    public ResponseDTO deleteBookmark(@Valid Long bookmarkId) {
        Optional<Bookmark> chkBookmark = bookmarkRepo.findById(bookmarkId);

        if (chkBookmark.isPresent()) {
            bookmarkRepo.deleteById(bookmarkId);
            return new ResponseDTO("Bookmark deleted Successfully");
        } else {
            logger.warn("Bookmark not found");
            logger.info("Exit from deleting bookmark.");
            throw new ApplicationException(msgSrc.getMessage("Bookmark.NotFound", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
    }

}
