package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.jwt.VerifyRequest;
import com.getyourtickets.dto.jwt.VerifyResponse;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.dto.usersignup.UserSignupResponse;
import com.getyourtickets.service.AuthenticationService;
import com.getyourtickets.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserSignupRequest request) {
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

    @PostMapping("/token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = authenticationService.generateToken(request);
        String message;
        if (response == null) {
            message = "Username or password is incorrect";
        } else {
            message = "Token generated successfully";
        }

        return new ResponseEntity<>(
                ApiResponse.builder().code(200)
                        .message(message)
                        .result(response)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyToken(@RequestBody VerifyRequest request) throws JOSEException, ParseException {
        boolean isVerified = authenticationService.verifyToken(request);
        VerifyResponse response = VerifyResponse.builder()
                .valid(isVerified)
                .build();
        return ResponseEntity.ok().body(ApiResponse.builder().code(200).result(response).build());
    }
}
