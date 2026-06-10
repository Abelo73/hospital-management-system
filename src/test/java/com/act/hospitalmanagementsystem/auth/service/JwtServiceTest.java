package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private User testUser;
    private String secretKey = "testSecretKeyForTestingPurposesOnlyMinimum256BitsLongEnough";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 604800000L);

        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName("PATIENT");
        role.setDescription("Patient role");

        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEnabled(true);
        testUser.setRoles(new HashSet<>());
        testUser.getRoles().add(role);
    }

    @Test
    void testGenerateToken_Success() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGenerateRefreshToken_Success() {
        String refreshToken = jwtService.generateRefreshToken(testUser);

        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
    }

    @Test
    void testExtractUsername_Success() {
        String token = jwtService.generateToken(testUser);
        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testExtractUsername_InvalidToken() {
        assertThrows(Exception.class, () -> jwtService.extractUsername("invalid.token.here"));
    }

    @Test
    void testIsTokenValid_Success() {
        String token = jwtService.generateToken(testUser);
        boolean isValid = jwtService.isTokenValid(token, "testuser");

        assertTrue(isValid);
    }

    @Test
    void testIsTokenValid_WrongUsername() {
        String token = jwtService.generateToken(testUser);
        boolean isValid = jwtService.isTokenValid(token, "wronguser");

        assertFalse(isValid);
    }

    @Test
    void testIsTokenValid_InvalidToken() {
        boolean isValid = jwtService.isTokenValid("invalid.token", "testuser");

        assertFalse(isValid);
    }

    @Test
    void testIsTokenExpired_NotExpired() {
        String token = jwtService.generateToken(testUser);
        boolean isExpired = jwtService.isTokenExpired(token);

        assertFalse(isExpired);
    }

    @Test
    void testExtractClaim_Success() {
        String token = jwtService.generateToken(testUser);
        String email = jwtService.extractClaim(token, claims -> claims.getSubject());

        assertEquals("testuser", email);
    }
}
