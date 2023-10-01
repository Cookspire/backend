package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.CommentDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.CommentService;

import jakarta.validation.Valid;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public CommentDTO persistComment(CommentDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistComment'");
    }

    @Override
    public CommentDTO fetchComment(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchComment'");
    }

    @Override
    public List<CommentDTO> fetchAllComment(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllComment'");
    }

    @Override
    public Object fetchAllCommentByPost(@Valid Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllCommentByPost'");
    }

    @Override
    public ResponseDTO deleteComment(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }
    
}
