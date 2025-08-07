package com.getyourtickets.service;

import com.getyourtickets.dto.user.UserResponse;
import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.model.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    void insertUser(UserSignupRequest request);
    UserResponse getUserResponseById(int id);
    List<UserResponse> getAllUsers();
}
