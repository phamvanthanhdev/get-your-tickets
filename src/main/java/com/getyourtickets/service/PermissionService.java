package com.getyourtickets.service;

import com.getyourtickets.dto.permission.PermissionRequest;
import com.getyourtickets.dto.permission.PermissionResponse;
import com.getyourtickets.model.Permission;

import java.util.List;

public interface PermissionService {
    Permission getAllPermissions();
    PermissionResponse getPermissionById(Long id);
    PermissionResponse createPermission(PermissionRequest permission);
    void deletePermission(Long id);

    List<Permission> getPermissionsByRoleId(Long roleId);
}
