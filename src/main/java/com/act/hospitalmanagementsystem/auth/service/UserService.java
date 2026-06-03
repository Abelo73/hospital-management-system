package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.CreateUserRequest;
import com.act.hospitalmanagementsystem.auth.dto.ResetPasswordRequest;
import com.act.hospitalmanagementsystem.auth.dto.UpdateUserRequest;
import com.act.hospitalmanagementsystem.auth.dto.UserDTO;
import com.act.hospitalmanagementsystem.auth.entity.ApprovalRequest;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import com.act.hospitalmanagementsystem.auth.mapper.UserMapper;
import com.act.hospitalmanagementsystem.auth.repository.ApprovalRequestRepository;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApprovalRequestRepository approvalRequestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDTO dto = userMapper.toDTO(user);
                    dto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()));
                    dto.setApprovalStatus(user.getApprovalStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserDTO dto = userMapper.toDTO(user);
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        dto.setApprovalStatus(user.getApprovalStatus().name());
        return dto;
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserDTO dto = userMapper.toDTO(user);
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        dto.setApprovalStatus(user.getApprovalStatus().name());
        return dto;
    }

    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("User", "authenticated", null);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserDTO dto = userMapper.toDTO(user);
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        dto.setApprovalStatus(user.getApprovalStatus().name());
        return dto;
    }

    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists", "USERNAME_EXISTS");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists", "EMAIL_EXISTS");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByNameAndDeletedFalse(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
                roles.add(role);
            }
        } else {
            Role defaultRole = roleRepository.findByNameAndDeletedFalse("RECEPTIONIST")
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "RECEPTIONIST"));
            roles.add(defaultRole);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // Automatically create an approval request for the new user
        if (savedUser.getApprovalStatus() == ApprovalStatus.PENDING_SUBMISSION) {
            ApprovalRequest approvalRequest = new ApprovalRequest();
            approvalRequest.setUser(savedUser);
            approvalRequest.setRequestType("USER_CREATION");
            approvalRequest.setRequestedRole(roles.stream().map(Role::getName).findFirst().orElse(null));
            approvalRequest.setStatus(ApprovalStatus.PENDING_APPROVAL);
            approvalRequest.setPriority(5);
            approvalRequest.setSubmittedAt(LocalDateTime.now());
            approvalRequest.setSubmittedBy("SYSTEM");
            approvalRequest.setDocumentsRequired(false);
            approvalRequest.setDocumentsVerified(true);
            approvalRequestRepository.save(approvalRequest);

            // Update user status to PENDING_APPROVAL
            savedUser.setApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
            savedUser.setSubmittedAt(LocalDateTime.now());
            userRepository.save(savedUser);
        }

        UserDTO dto = userMapper.toDTO(savedUser);
        dto.setRoles(savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        dto.setApprovalStatus(savedUser.getApprovalStatus().name());
        return dto;
    }

    @Transactional
    public UserDTO updateUser(UUID id, UpdateUserRequest request) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists", "EMAIL_EXISTS");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        if (request.getApprovalStatus() != null) {
            try {
                user.setApprovalStatus(com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus.valueOf(request.getApprovalStatus()));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid approval status", "INVALID_APPROVAL_STATUS");
            }
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> newRoles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByNameAndDeletedFalse(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
                newRoles.add(role);
            }
            user.setRoles(newRoles);
        }

        User updatedUser = userRepository.save(user);
        UserDTO dto = userMapper.toDTO(updatedUser);
        dto.setRoles(updatedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        dto.setApprovalStatus(updatedUser.getApprovalStatus().name());
        return dto;
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsernameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", request.getUsername()));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void assignRole(UUID userId, String roleName) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findByNameAndDeletedFalse(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));

        if (!user.getRoles().contains(role)) {
            user.addRole(role);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeRole(UUID userId, String roleName) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findByNameAndDeletedFalse(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));

        if (user.getRoles().contains(role)) {
            user.removeRole(role);
            userRepository.save(user);
        }
    }

    public List<UserDTO> getUsersByRole(String roleName) {
        List<User> users = userRepository.findByRoleName(roleName);
        return users.stream()
                .map(user -> {
                    UserDTO dto = userMapper.toDTO(user);
                    dto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()));
                    dto.setApprovalStatus(user.getApprovalStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserDTO> searchUsersByRole(String roleName, String searchTerm) {
        List<User> users = userRepository.findByRoleNameAndSearchTerm(roleName, searchTerm);
        return users.stream()
                .map(user -> {
                    UserDTO dto = userMapper.toDTO(user);
                    dto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()));
                    dto.setApprovalStatus(user.getApprovalStatus().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
