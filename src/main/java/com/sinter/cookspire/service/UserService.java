package com.sinter.cookspire.service;

import com.sinter.cookspire.dto.FollowerDTO;
import com.sinter.cookspire.dto.FollowerResponseDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.dto.UserResponseDTO;
import com.sinter.cookspire.dto.VerifyUserDTO;

import jakarta.validation.Valid;

public interface UserService {

    public UserDTO persistUser(UserDTO request);

    public UserDTO fetchUser(@Valid Long userId);

    public ResponseDTO deleteUser(@Valid Long userId);

    public FollowerDTO persistFollower(FollowerDTO request);

    public FollowerResponseDTO fetchAllFollowers(@Valid long userId);

    public UserResponseDTO verifyUser(VerifyUserDTO request);

}
