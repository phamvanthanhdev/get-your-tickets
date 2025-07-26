package com.getyourtickets.dto.usersignup;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "message", "username" })
public class UserSignupResponse {
    private String username;
    private String message;

    public static UserSignupResponse from(String username, String message) {
        return UserSignupResponse.builder()
                .username(username)
                .message(message)
                .build();
    }
}
