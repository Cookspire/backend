package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.BookmarkDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface BookmarkService {

    BookmarkDTO persistBookmark(BookmarkDTO request);

    List<BookmarkDTO> fetchAllBookmark(@Valid Long userId);

    ResponseDTO deleteBookmark(@Valid Long bookmarkId);

}
