package com.getyourtickets.service;

import com.getyourtickets.dto.ApiResponse;

public class CommonService {
    public static ApiResponse<Object> buildResponse(int code, String message, Object result) {
        return new ApiResponse<>(code, message, result);
    }
}
