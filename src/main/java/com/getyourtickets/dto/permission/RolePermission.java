package com.getyourtickets.dto.permission;

import lombok.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {
    private String roleName;
    private List<String> permissionNames;
}
