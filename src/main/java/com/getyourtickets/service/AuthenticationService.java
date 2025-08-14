package com.getyourtickets.service;

import com.getyourtickets.dto.jwt.VerifyRequest;
import com.getyourtickets.dto.logout.LogoutRequest;
import com.getyourtickets.dto.refresh.RefreshRequest;
import com.getyourtickets.dto.refresh.RefreshResponse;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    boolean verifyToken(String token) throws JOSEException, ParseException;
    UserLoginResponse generateToken(UserLoginRequest request);
    void logout(LogoutRequest request) throws JOSEException, ParseException;
    RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
