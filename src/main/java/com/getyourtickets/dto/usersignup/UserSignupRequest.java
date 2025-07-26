package com.getyourtickets.dto.usersignup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupRequest {
    private String username;
    private String password;
    private String email;
    @JsonProperty("full_name")
    private String fullName;
}
