package com.sinter.cookspire.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.service.PostService;

import jakarta.validation.Valid;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public PostDTO persistPost(PostDTO request) {
        throw new UnsupportedOperationException("Unimplemented method 'persistPost'");
    }

    @Override
    public PostDTO fetchPost(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchPost'");
    }

    @Override
    public List<PostDTO> fetchAllPost(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAllPost'");
    }

    @Override
    public ResponseDTO deletePost(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
    }

}
