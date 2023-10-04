package com.sinter.cookspire.dto;

import java.util.List;

import lombok.Data;

@Data
public class FollowerResponseDTO {

    private List<FollowersDataDTO> followers;

    private List<FollowersDataDTO> following;

}
