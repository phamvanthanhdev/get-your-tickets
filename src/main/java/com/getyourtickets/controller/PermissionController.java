package com.getyourtickets.controller;

import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.dto.permission.PermissionRequest;
import com.getyourtickets.dto.permission.PermissionResponse;
import com.getyourtickets.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/getAll")
    public String getAllPermissions() {
        // This method would typically return a list of permissions.
        // For demonstration purposes, we return a simple string.
        return "List of all permissions";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPermissionById(@PathVariable Long id) {
        PermissionResponse permissionResponse = permissionService.getPermissionById(id);
        return new ResponseEntity<>(
                ApiResponse.builder()
                    .code(200)
                    .message("Permission retrieved successfully")
                    .result(permissionResponse)
                    .build()
                , HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPermission(@RequestBody @Valid PermissionRequest permission) {
        PermissionResponse response = permissionService.createPermission(permission);
        return new ResponseEntity<>(
                ApiResponse.builder()
                    .code(200)
                    .message("Permission created successfully")
                    .result(response)
                    .build()
                , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deletePermission(@PathVariable Long id) {
        // This method would typically delete a permission by its ID.
        // For demonstration purposes, we return a simple string.
        return "Permission deleted with ID: " + id;
    }
}
