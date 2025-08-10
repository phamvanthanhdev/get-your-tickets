package com.getyourtickets.dto.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse implements Serializable {
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
}
