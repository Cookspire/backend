package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.PostDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface PostService {

    PostDTO persistPost(PostDTO request);

    PostDTO fetchPost(@Valid Long postId);

    List<PostDTO> fetchAllPost(@Valid Long userId);

    List<PostDTO> fetchAllFollowersPost(Long userId);

    ResponseDTO deletePost(@Valid Long postId);

     List<PostDTO> fetchAllTrendingPost(Long userId);

}
