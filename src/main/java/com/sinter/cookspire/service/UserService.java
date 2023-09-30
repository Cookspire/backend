package com.sinter.cookspire.service;

import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;

import jakarta.validation.Valid;

public interface UserService {

    public UserDTO persistUser(UserDTO request);

    public UserDTO fetchUser(@Valid Long userId);

    public ResponseDTO deleteUser(@Valid Long userId);

}
