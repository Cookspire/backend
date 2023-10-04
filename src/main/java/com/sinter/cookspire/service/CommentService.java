package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.CommentDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface CommentService {

    CommentDTO persistComment(CommentDTO request);

    List<CommentDTO> fetchAllCommentByPost(@Valid Long postId);

    ResponseDTO deleteComment(@Valid Long commentId);

}
