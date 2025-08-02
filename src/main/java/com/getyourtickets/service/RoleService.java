package com.getyourtickets.service;

import com.getyourtickets.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    List<Role> getRolesByUserId(Integer userId);
}
