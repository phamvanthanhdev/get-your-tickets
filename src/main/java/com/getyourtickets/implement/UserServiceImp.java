package com.getyourtickets.implement;

import com.getyourtickets.dto.permission.RolePermission;
import com.getyourtickets.dto.user.UserResponse;
import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.exception.ErrorEnum;
import com.getyourtickets.exception.GytException;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.Permission;
import com.getyourtickets.model.Role;
import com.getyourtickets.model.User;
import com.getyourtickets.service.PermissionService;
import com.getyourtickets.service.RoleService;
import com.getyourtickets.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public User getUserByUsername(String username) {
        return Optional.ofNullable(userMapper.getUserByUsername(username))
                .orElseThrow(() -> new GytException(ErrorEnum.USER_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertUser(UserSignupRequest request) {
        User existingUser = userMapper.getUserByUsername(request.getUsername());
        if (existingUser != null) {
            throw new GytException(ErrorEnum.USER_ALREADY_EXISTS);
        }

        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("username", request.getUsername());
        insertMap.put("password", hashPassword(request.getPassword()));
        insertMap.put("email", request.getEmail());
        insertMap.put("fullName", request.getFullName());

        userMapper.insertUser(insertMap);

    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserResponseById(int id) {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new GytException(ErrorEnum.USER_NOT_FOUND);
        }
        List<Role> roles = roleService.getRolesByUserId(id);
        Set<RolePermission> rolePermissions = new HashSet<>();
        for (Role role : roles) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleName(role.getName());
            List<Permission> permissions = permissionService.getPermissionsByRoleId(Long.valueOf(role.getId()));
            Set<String> permissionNames = new HashSet<>();
            permissions.forEach(permission -> {
                permissionNames.add(permission.getName());
            });
            rolePermission.setPermissionNames(permissionNames.stream().toList());
            rolePermissions.add(rolePermission);
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .rolePermissions(rolePermissions)
                .build();
    }

    private String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        List<User> users = userMapper.getAllUsers();
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(user -> {
                    List<Role> roles = roleService.getRolesByUserId(user.getId());
                    Set<RolePermission> rolePermissions = new HashSet<>();
                    for (Role role : roles) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleName(role.getName());
                        List<Permission> permissions = permissionService.getPermissionsByRoleId(Long.valueOf(role.getId()));
                        Set<String> permissionNames = new HashSet<>();
                        permissions.forEach(permission -> {
                            permissionNames.add(permission.getName());
                        });
                        rolePermission.setPermissionNames(permissionNames.stream().toList());
                        rolePermissions.add(rolePermission);
                    }
                    return UserResponse.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .fullName(user.getFullName())
                            .rolePermissions(rolePermissions)
                            .build();
                })
                .toList();
    }
}
