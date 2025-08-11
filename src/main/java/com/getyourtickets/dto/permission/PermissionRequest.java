package com.getyourtickets.dto.permission;

import com.getyourtickets.validator.PermissionNameValidator;
import lombok.Data;

@Data
public class PermissionRequest {
    @PermissionNameValidator(message = "INVALID_PERMISSION_NAME")
    private String name;
    private String description;
}
