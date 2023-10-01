package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.BookmarkDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.BookmarkService;

import jakarta.validation.Valid;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Override
    public BookmarkDTO persistBookmark(BookmarkDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistBookmark'");
    }

    @Override
    public BookmarkDTO fetchBookmark(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchBookmark'");
    }

    @Override
    public List<BookmarkDTO> fetchAllBookmark(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllBookmark'");
    }

    @Override
    public ResponseDTO deleteBookmark(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBookmark'");
    }
    
}
