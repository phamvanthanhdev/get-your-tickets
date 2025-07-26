package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.dto.usersignup.UserSignupResponse;
import com.getyourtickets.service.AuthenticationService;
import com.getyourtickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest request) {
        boolean isAuthenticated = authenticationService.authenticate(request);
        UserLoginResponse response = UserLoginResponse.builder()
                .message(isAuthenticated ? "Login successful" : "Username or password is incorrect")
                .build();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(200)
                        .result(response)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody UserSignupRequest request) {
        userService.insertUser(request);

        UserSignupResponse response = UserSignupResponse.builder()
                .username(request.getUsername())
                .message("User registered successfully")
                .build();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(201)
                        .result(response)
                        .build(),
                HttpStatus.CREATED
        );
    }
}
