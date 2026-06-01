package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.CreatePermissionRequest;
import com.act.hospitalmanagementsystem.auth.dto.PermissionDTO;
import com.act.hospitalmanagementsystem.auth.dto.UpdatePermissionRequest;
import com.act.hospitalmanagementsystem.auth.service.PermissionService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<BaseResponseDTO<List<PermissionDTO>>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(BaseResponseDTO.success(permissions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<BaseResponseDTO<PermissionDTO>> getPermissionById(@PathVariable UUID id) {
        PermissionDTO permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(permission));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<BaseResponseDTO<PermissionDTO>> getPermissionByName(@PathVariable String name) {
        PermissionDTO permission = permissionService.getPermissionByName(name);
        return ResponseEntity.ok(BaseResponseDTO.success(permission));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_WRITE')")
    public ResponseEntity<BaseResponseDTO<PermissionDTO>> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        PermissionDTO permission = permissionService.createPermission(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Permission created successfully", permission));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_WRITE')")
    public ResponseEntity<BaseResponseDTO<PermissionDTO>> updatePermission(@PathVariable UUID id, @Valid @RequestBody UpdatePermissionRequest request) {
        PermissionDTO permission = permissionService.updatePermission(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success("Permission updated successfully", permission));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deletePermission(@PathVariable UUID id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Permission deleted successfully", null));
    }
}
