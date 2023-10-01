package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.RepliesDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.RepliesService;

import jakarta.validation.Valid;

@Service
public class RepliesServiceImpl implements RepliesService {

    @Override
    public RepliesDTO persistReplies(RepliesDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistReplies'");
    }

    @Override
    public RepliesDTO fetchReplies(@Valid Long repliesId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchReplies'");
    }

    @Override
    public List<RepliesDTO> fetchAllReplies(@Valid Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllReplies'");
    }

    @Override
    public ResponseDTO deleteReplies(@Valid Long repliesId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteReplies'");
    }

}
