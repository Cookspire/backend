package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.FollowerResponseDTO;
import com.sinter.cookspire.dto.ImageRequestDTO;
import com.sinter.cookspire.dto.JWTResponseDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.SpotlightRequestDTO;
import com.sinter.cookspire.dto.SpotlightResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.dto.UserGeneralAnalysisDTO;
import com.sinter.cookspire.dto.VerifyUserDTO;

import jakarta.validation.Valid;

public interface UserService {

    public UserDTO persistUser(UserDTO request);

    public UserDTO fetchUser(@Valid String email);

    public ResponseDTO deleteUser(@Valid Long userId);

    public FollowerDTO persistFollower(FollowerDTO request);

    public FollowerResponseDTO fetchAllFollowers(@Valid long userId);

    public JWTResponseDTO verifyUser(VerifyUserDTO request);

    public UserGeneralAnalysisDTO fetchGeneralUserAnalysis(@Valid long userId);

    public UserDTO uploadProfilePicture(ImageRequestDTO imageDetails);

    public SpotlightResponseDTO fetchProfileSpotLight(SpotlightRequestDTO request);

    public List<SpotlightResponseDTO> fetchSuggestedUsers(@Valid String email);

}
