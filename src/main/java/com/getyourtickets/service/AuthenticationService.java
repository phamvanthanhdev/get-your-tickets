package com.getyourtickets.service;

import com.getyourtickets.dto.jwt.VerifyRequest;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    boolean verifyToken(VerifyRequest request) throws JOSEException, ParseException;
    UserLoginResponse generateToken(UserLoginRequest request);
}
