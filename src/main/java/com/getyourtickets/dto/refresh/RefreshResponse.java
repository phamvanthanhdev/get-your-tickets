package com.getyourtickets.dto.refresh;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshResponse {
    private String token;
}
