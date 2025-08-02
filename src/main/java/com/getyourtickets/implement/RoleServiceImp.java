package com.getyourtickets.implement;

import com.getyourtickets.mapper.RoleMapper;
import com.getyourtickets.model.Role;
import com.getyourtickets.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }
    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
