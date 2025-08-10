package com.getyourtickets.mapper;

import com.getyourtickets.model.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PermissionMapper {
    List<Permission> getAllPermissions();
    Permission getPermissionById(Long id);
    void createPermission(Map<String, Object> params);
    void deletePermission(Long id);
    List<Permission> getPermissionsByRoleId(Long roleId);
}
