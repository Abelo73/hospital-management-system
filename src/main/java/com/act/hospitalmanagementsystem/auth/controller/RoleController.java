package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.CreateRoleRequest;
import com.act.hospitalmanagementsystem.auth.dto.RoleDTO;
import com.act.hospitalmanagementsystem.auth.dto.UpdateRoleRequest;
import com.act.hospitalmanagementsystem.auth.service.RoleService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<BaseResponseDTO<List<RoleDTO>>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(BaseResponseDTO.success(roles));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<BaseResponseDTO<RoleDTO>> getRoleById(@PathVariable UUID id) {
        RoleDTO role = roleService.getRoleById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(role));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<BaseResponseDTO<RoleDTO>> getRoleByName(@PathVariable String name) {
        RoleDTO role = roleService.getRoleByName(name);
        return ResponseEntity.ok(BaseResponseDTO.success(role));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ResponseEntity<BaseResponseDTO<RoleDTO>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleDTO role = roleService.createRole(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Role created successfully", role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ResponseEntity<BaseResponseDTO<RoleDTO>> updateRole(@PathVariable UUID id, @Valid @RequestBody UpdateRoleRequest request) {
        RoleDTO role = roleService.updateRole(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success("Role updated successfully", role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Role deleted successfully", null));
    }

    @PostMapping("/{id}/permissions/{permissionName}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> assignPermission(@PathVariable UUID id, @PathVariable String permissionName) {
        roleService.assignPermission(id, permissionName);
        return ResponseEntity.ok(BaseResponseDTO.success("Permission assigned successfully", null));
    }

    @DeleteMapping("/{id}/permissions/{permissionName}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> removePermission(@PathVariable UUID id, @PathVariable String permissionName) {
        roleService.removePermission(id, permissionName);
        return ResponseEntity.ok(BaseResponseDTO.success("Permission removed successfully", null));
    }
}
