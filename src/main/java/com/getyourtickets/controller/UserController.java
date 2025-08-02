package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.user.UserResponse;
import com.getyourtickets.service.MessageService;
import com.getyourtickets.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Integer id) {
        System.out.println("Current User: " + messageService.getCurrentUser());
        System.out.println("Current User Role: " + messageService.getCurrentUserRole().toString());

        try {
            UserResponse response = userService.getUserResponseById(id);
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .code(200)
                    .result(response)
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(
                    ApiResponse.builder()
                    .code(500)
                    .message("Something went wrong")
                    .build());
        }
    }
}
