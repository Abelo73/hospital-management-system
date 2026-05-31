package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.LoginRequest;
import com.act.hospitalmanagementsystem.auth.dto.LoginResponse;
import com.act.hospitalmanagementsystem.auth.dto.RegisterRequest;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
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
        
        // Set approval status - user needs approval before being enabled
        user.setApprovalStatus(ApprovalStatus.PENDING_SUBMISSION);
        user.setSubmittedAt(LocalDateTime.now());
        user.setEnabled(false); // Disabled until approved
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        // Determine if verification is required based on requested role
        boolean requiresVerification = false;
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                if (roleName.equals("DOCTOR") || roleName.equals("NURSE") || 
                    roleName.equals("PHARMACIST") || roleName.equals("LAB_TECHNICIAN")) {
                    requiresVerification = true;
                }
                Role role = roleRepository.findByNameAndDeletedFalse(roleName)
                        .orElseThrow(() -> new BadRequestException("Role not found: " + roleName, "ROLE_NOT_FOUND"));
                roles.add(role);
            }
        }
        
        user.setRequiresVerification(requiresVerification);
        if (requiresVerification) {
            user.setApprovalStatus(ApprovalStatus.PENDING_VERIFICATION);
        }
        
        // Don't assign roles yet - they'll be assigned after approval
        user.setRoles(new HashSet<>());

        User savedUser = userRepository.save(user);

        // Return tokens but user won't be able to access protected endpoints until approved
        String accessToken = jwtService.generateToken(savedUser.getUsername());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getUsername());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .roles(new HashSet<>()) // Empty until approved
                .permissions(new HashSet<>()) // Empty until approved
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsernameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found", "USER_NOT_FOUND"));

        // Check approval status and provide appropriate error message
        if (user.getApprovalStatus() == ApprovalStatus.PENDING_SUBMISSION) {
            throw new BadRequestException(
                    "Your registration is pending submission. Please complete your registration process.",
                    "PENDING_SUBMISSION"
            );
        }

        if (user.getApprovalStatus() == ApprovalStatus.PENDING_VERIFICATION) {
            throw new BadRequestException(
                    "Your account is pending document verification. Please upload your documents and wait for verification.",
                    "PENDING_VERIFICATION"
            );
        }

        if (user.getApprovalStatus() == ApprovalStatus.PENDING_APPROVAL) {
            throw new BadRequestException(
                    "Your account is pending approval. Please wait for the approval process to complete.",
                    "PENDING_APPROVAL"
            );
        }

        if (user.getApprovalStatus() == ApprovalStatus.REJECTED) {
            throw new BadRequestException(
                    "Your account has been rejected. Reason: " + (user.getRejectionReason() != null ? user.getRejectionReason() : "Not specified"),
                    "ACCOUNT_REJECTED"
            );
        }

        if (user.getApprovalStatus() == ApprovalStatus.SUSPENDED) {
            throw new BadRequestException(
                    "Your account has been suspended. Please contact support.",
                    "ACCOUNT_SUSPENDED"
            );
        }

        if (!user.getEnabled()) {
            throw new BadRequestException("User account is disabled", "ACCOUNT_DISABLED");
        }

        String accessToken = jwtService.generateToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .permissions(user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(permission -> permission.getName())
                        .collect(Collectors.toSet()))
                .build();
    }

    public LoginResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            User user = userRepository.findByUsernameAndDeletedFalse(username)
                    .orElseThrow(() -> new BadRequestException("User not found", "USER_NOT_FOUND"));

            if (jwtService.isTokenValid(refreshToken, username)) {
                String newAccessToken = jwtService.generateToken(username);
                String newRefreshToken = jwtService.generateRefreshToken(username);

                return LoginResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .tokenType("Bearer")
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                        .permissions(user.getRoles().stream()
                                .flatMap(role -> role.getPermissions().stream())
                                .map(permission -> permission.getName())
                                .collect(Collectors.toSet()))
                        .build();
            }
        }

        throw new BadRequestException("Invalid refresh token", "INVALID_REFRESH_TOKEN");
    }
}
