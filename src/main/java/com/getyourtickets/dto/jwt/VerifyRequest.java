package com.getyourtickets.dto.jwt;

import lombok.Data;

@Data
public class VerifyRequest {
    private String token;
}
