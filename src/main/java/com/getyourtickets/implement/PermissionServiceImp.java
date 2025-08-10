package com.getyourtickets.implement;

import com.getyourtickets.dto.permission.PermissionRequest;
import com.getyourtickets.dto.permission.PermissionResponse;
import com.getyourtickets.mapper.PermissionMapper;
import com.getyourtickets.model.Permission;
import com.getyourtickets.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImp implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission getAllPermissions() {
        // Implementation logic to retrieve all permissions
        return new Permission();
    }

    @Override
    @PreAuthorize("hasAuthority('READ_DATA')")
//    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse getPermissionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Permission ID cannot be null");
        }
        Permission permission = permissionMapper.getPermissionById(id);
        if (permission == null) {
            throw new IllegalArgumentException("Permission not found for ID: " + id);
        }
        PermissionResponse response = new PermissionResponse();
        response.setName(permission.getName());
        response.setDescription(permission.getDescription());
        response.setCreatedAt(permission.getCreatedAt());
        response.setUpdatedAt(permission.getUpdatedAt());
        return response;
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest permission) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", permission.getName());
        params.put("description", permission.getDescription());

        permissionMapper.createPermission(params);
        return getPermissionById(Long.parseLong(String.valueOf(params.get("id"))));
    }

    @Override
    public void deletePermission(Long id) {
        // Implementation logic to delete a permission by ID
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        return permissionMapper.getPermissionsByRoleId(roleId);
    }
}
