package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.CommentDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface CommentService {

    CommentDTO persistComment(CommentDTO request);

    CommentDTO fetchComment(@Valid Long userId);

    List<CommentDTO> fetchAllComment(@Valid Long userId);

    // change the response
    Object fetchAllCommentByPost(@Valid Long postId);

    ResponseDTO deleteComment(@Valid Long userId);

}
