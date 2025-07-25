package com.getyourtickets.service;

import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.model.User;

public interface UserService {
    User getUserByUsername(String username);
    void insertUser(UserSignupRequest request);
}
