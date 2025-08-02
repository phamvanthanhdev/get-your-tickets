package com.getyourtickets.mapper;

import com.getyourtickets.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getAllRoles();
    List<Role> getRolesByUserId(Integer userId);
}
