package com.getyourtickets.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Logout {
    private Integer id;
    private String jwtId;
    private String token;
    private String expiredTime;
    private String createdAt;
}
