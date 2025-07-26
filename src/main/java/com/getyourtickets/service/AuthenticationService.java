package com.getyourtickets.service;

import com.getyourtickets.dto.userlogin.UserLoginRequest;

public interface AuthenticationService {
    boolean authenticate(UserLoginRequest request);
}
