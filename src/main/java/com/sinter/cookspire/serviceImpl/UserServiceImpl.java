package com.sinter.cookspire.serviceImpl;

import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.dto.UserDTO;
import com.sinter.cookspire.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO persistUser(UserDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistUser'");
    }

    @Override
    public UserDTO fetchUser(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchUser'");
    }

    @Override
    public ResponseDTO deleteUser(@Valid Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

}
