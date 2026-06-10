package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.LoginRequest;
import com.act.hospitalmanagementsystem.auth.dto.LoginResponse;
import com.act.hospitalmanagementsystem.auth.dto.RegisterRequest;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private Role testRole;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(java.util.UUID.randomUUID());
        testRole.setName("PATIENT");
        testRole.setDescription("Patient role");

        testUser = new User();
        testUser.setId(java.util.UUID.randomUUID());
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEnabled(true);
        testUser.setRoles(new HashSet<>());
        testUser.getRoles().add(testRole);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("new@example.com");
        registerRequest.setFirstName("New");
        registerRequest.setLastName("User");
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(testUser.getUsername())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(testUser.getUsername())).thenReturn("refreshToken");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("testuser", response.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> authService.login(loginRequest));
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void testLogin_InvalidPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        assertThrows(Exception.class, () -> authService.login(loginRequest));
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void testRegister_Success() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(roleRepository.findByName("PATIENT")).thenReturn(Optional.of(testRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(any(String.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(String.class))).thenReturn("refreshToken");

        LoginResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(userRepository, times(1)).existsByUsername("newuser");
        verify(userRepository, times(1)).existsByEmail("new@example.com");
        verify(roleRepository, times(1)).findByName("PATIENT");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UsernameExists() {
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        assertThrows(Exception.class, () -> authService.register(registerRequest));
        verify(userRepository, times(1)).existsByUsername("newuser");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRefreshToken_Success() {
        when(jwtService.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(jwtService.isTokenValid("validToken", "testuser")).thenReturn(true);
        when(jwtService.generateToken(testUser.getUsername())).thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(testUser.getUsername())).thenReturn("newRefreshToken");

        LoginResponse response = authService.refreshToken("validToken");

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
        verify(jwtService, times(1)).extractUsername("validToken");
        verify(jwtService, times(1)).isTokenValid("validToken", "testuser");
    }

    @Test
    void testRefreshToken_InvalidToken() {
        when(jwtService.extractUsername("invalidToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(jwtService.isTokenValid("invalidToken", "testuser")).thenReturn(false);

        assertThrows(Exception.class, () -> authService.refreshToken("invalidToken"));
        verify(jwtService, times(1)).isTokenValid("invalidToken", "testuser");
        verify(jwtService, never()).generateToken(any());
    }
}
