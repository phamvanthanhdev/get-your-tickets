package com.getyourtickets.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Set<String> roles;
}
