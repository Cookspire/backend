package com.sinter.cookspire.service;



import com.sinter.cookspire.dto.RefreshTokenDTO;
import com.sinter.cookspire.dto.ResponseDTO;

public interface RefreshTokenService {

    RefreshTokenDTO persistToken(RefreshTokenDTO request);

    RefreshTokenDTO getToken(String token);

    RefreshTokenDTO verifyToken(String token);

    ResponseDTO deleteToken(String token);

}
