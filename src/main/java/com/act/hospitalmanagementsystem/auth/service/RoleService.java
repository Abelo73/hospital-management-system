package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.CreateRoleRequest;
import com.act.hospitalmanagementsystem.auth.dto.RoleDTO;
import com.act.hospitalmanagementsystem.auth.dto.UpdateRoleRequest;
import com.act.hospitalmanagementsystem.auth.entity.Permission;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.mapper.RoleMapper;
import com.act.hospitalmanagementsystem.auth.repository.PermissionRepository;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> {
                    RoleDTO dto = roleMapper.toDTO(role);
                    dto.setPermissions(role.getPermissions().stream()
                            .map(Permission::getName)
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(UUID id) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        RoleDTO dto = roleMapper.toDTO(role);
        dto.setPermissions(role.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    public RoleDTO getRoleByName(String name) {
        Role role = roleRepository.findByNameAndDeletedFalse(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
        RoleDTO dto = roleMapper.toDTO(role);
        dto.setPermissions(role.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    @Transactional
    public RoleDTO createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new BadRequestException("Role name already exists", "ROLE_EXISTS");
        }

        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Set<Permission> permissions = new HashSet<>();
        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            for (String permissionName : request.getPermissions()) {
                Permission permission = permissionRepository.findByNameAndDeletedFalse(permissionName)
                        .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", permissionName));
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);
        RoleDTO dto = roleMapper.toDTO(savedRole);
        dto.setPermissions(savedRole.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    @Transactional
    public RoleDTO updateRole(UUID id, UpdateRoleRequest request) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getPermissions() != null) {
            Set<Permission> permissions = new HashSet<>();
            for (String permissionName : request.getPermissions()) {
                Permission permission = permissionRepository.findByNameAndDeletedFalse(permissionName)
                        .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", permissionName));
                permissions.add(permission);
            }
            role.setPermissions(permissions);
        }

        Role updatedRole = roleRepository.save(role);
        RoleDTO dto = roleMapper.toDTO(updatedRole);
        dto.setPermissions(updatedRole.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    @Transactional
    public void deleteRole(UUID id) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        role.setDeleted(true);
        roleRepository.save(role);
    }

    @Transactional
    public void assignPermission(UUID roleId, String permissionName) {
        Role role = roleRepository.findByIdAndDeletedFalse(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        Permission permission = permissionRepository.findByNameAndDeletedFalse(permissionName)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", permissionName));

        if (!role.getPermissions().contains(permission)) {
            role.addPermission(permission);
            roleRepository.save(role);
        }
    }

    @Transactional
    public void removePermission(UUID roleId, String permissionName) {
        Role role = roleRepository.findByIdAndDeletedFalse(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        Permission permission = permissionRepository.findByNameAndDeletedFalse(permissionName)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", permissionName));

        if (role.getPermissions().contains(permission)) {
            role.removePermission(permission);
            roleRepository.save(role);
        }
    }
}
