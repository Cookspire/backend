package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.RepliesDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface RepliesService {

    RepliesDTO persistReplies(RepliesDTO request);

    RepliesDTO fetchReplies(@Valid Long repliesId);

    List<RepliesDTO> fetchAllReplies(@Valid Long commentId);

    ResponseDTO deleteReplies(@Valid Long repliesId);

}
