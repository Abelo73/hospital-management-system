package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setName("PATIENT");
        testRole.setDescription("Patient role");
        entityManager.persist(testRole);

        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEnabled(true);
        testUser.setApprovalStatus(ApprovalStatus.APPROVED);
        testUser.setRoles(Set.of(testRole));
        entityManager.persist(testUser);
        entityManager.flush();
    }

    @Test
    void testFindByUsername_Success() {
        Optional<User> found = userRepository.findByUsername("testuser");

        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByUsername_NotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");

        assertFalse(found.isPresent());
    }

    @Test
    void testFindByEmail_Success() {
        Optional<User> found = userRepository.findByEmail("test@example.com");

        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(found.isPresent());
    }

    @Test
    void testExistsByUsername_Success() {
        boolean exists = userRepository.existsByUsername("testuser");

        assertTrue(exists);
    }

    @Test
    void testExistsByUsername_NotExists() {
        boolean exists = userRepository.existsByUsername("nonexistent");

        assertFalse(exists);
    }

    @Test
    void testExistsByEmail_Success() {
        boolean exists = userRepository.existsByEmail("test@example.com");

        assertTrue(exists);
    }

    @Test
    void testExistsByEmail_NotExists() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }

    @Test
    void testFindByApprovalStatus_Success() {
        Set<User> users = userRepository.findByApprovalStatus(ApprovalStatus.APPROVED);

        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("testuser")));
    }

    @Test
    void testFindByApprovalStatus_Empty() {
        Set<User> users = userRepository.findByApprovalStatus(ApprovalStatus.PENDING_SUBMISSION);

        assertTrue(users.isEmpty());
    }

    @Test
    void testFindByEnabledTrue_Success() {
        Set<User> users = userRepository.findByEnabledTrue();

        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("testuser")));
    }

    @Test
    void testFindByEnabledTrue_Empty() {
        testUser.setEnabled(false);
        entityManager.persist(testUser);
        entityManager.flush();

        Set<User> users = userRepository.findByEnabledTrue();

        assertFalse(users.stream().anyMatch(u -> u.getUsername().equals("testuser")));
    }
}
