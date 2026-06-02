package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.CreateUserRequest;
import com.act.hospitalmanagementsystem.auth.dto.ResetPasswordRequest;
import com.act.hospitalmanagementsystem.auth.dto.UpdateUserRequest;
import com.act.hospitalmanagementsystem.auth.dto.UserDTO;
import com.act.hospitalmanagementsystem.auth.service.UserService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<BaseResponseDTO<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(BaseResponseDTO.success(users));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponseDTO<UserDTO>> getCurrentUser() {
        UserDTO user = userService.getCurrentUser();
        return ResponseEntity.ok(BaseResponseDTO.success(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<BaseResponseDTO<UserDTO>> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(user));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<BaseResponseDTO<UserDTO>> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(BaseResponseDTO.success(user));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<BaseResponseDTO<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return ResponseEntity.ok(BaseResponseDTO.success("User created successfully", user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<BaseResponseDTO<UserDTO>> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        UserDTO user = userService.updateUser(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success("User updated successfully", user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(BaseResponseDTO.success("User deleted successfully", null));
    }

    @PostMapping("/reset-password")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Password reset successfully", null));
    }

    @PostMapping("/{id}/roles/{roleName}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> assignRole(@PathVariable UUID id, @PathVariable String roleName) {
        userService.assignRole(id, roleName);
        return ResponseEntity.ok(BaseResponseDTO.success("Role assigned successfully", null));
    }

    @DeleteMapping("/{id}/roles/{roleName}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> removeRole(@PathVariable UUID id, @PathVariable String roleName) {
        userService.removeRole(id, roleName);
        return ResponseEntity.ok(BaseResponseDTO.success("Role removed successfully", null));
    }

    @GetMapping("/role/{roleName}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<BaseResponseDTO<List<UserDTO>>> getUsersByRole(@PathVariable String roleName) {
        List<UserDTO> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(BaseResponseDTO.success(users));
    }

    @GetMapping("/role/{roleName}/search")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<BaseResponseDTO<List<UserDTO>>> searchUsersByRole(
            @PathVariable String roleName,
            @RequestParam String searchTerm
    ) {
        List<UserDTO> users = userService.searchUsersByRole(roleName, searchTerm);
        return ResponseEntity.ok(BaseResponseDTO.success(users));
    }
}
