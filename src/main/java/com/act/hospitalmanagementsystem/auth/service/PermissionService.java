package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.CreatePermissionRequest;
import com.act.hospitalmanagementsystem.auth.dto.PermissionDTO;
import com.act.hospitalmanagementsystem.auth.dto.UpdatePermissionRequest;
import com.act.hospitalmanagementsystem.auth.entity.Permission;
import com.act.hospitalmanagementsystem.auth.mapper.PermissionMapper;
import com.act.hospitalmanagementsystem.auth.repository.PermissionRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toDTOList(permissions);
    }

    public PermissionDTO getPermissionById(UUID id) {
        Permission permission = permissionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        return permissionMapper.toDTO(permission);
    }

    public PermissionDTO getPermissionByName(String name) {
        Permission permission = permissionRepository.findByNameAndDeletedFalse(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));
        return permissionMapper.toDTO(permission);
    }

    @Transactional
    public PermissionDTO createPermission(CreatePermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new BadRequestException("Permission name already exists", "PERMISSION_EXISTS");
        }

        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());

        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toDTO(savedPermission);
    }

    @Transactional
    public PermissionDTO updatePermission(UUID id, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        if (request.getDescription() != null) {
            permission.setDescription(request.getDescription());
        }

        Permission updatedPermission = permissionRepository.save(permission);
        return permissionMapper.toDTO(updatedPermission);
    }

    @Transactional
    public void deletePermission(UUID id) {
        Permission permission = permissionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        permission.setDeleted(true);
        permissionRepository.save(permission);
    }
}
