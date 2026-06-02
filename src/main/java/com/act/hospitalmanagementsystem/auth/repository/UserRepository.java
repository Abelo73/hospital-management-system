package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND u.id = :id")
    Optional<User> findByIdAndDeletedFalse(@Param("id") UUID id);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND u.username = :username")
    Optional<User> findByUsernameAndDeletedFalse(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND u.email = :email")
    Optional<User> findByEmailAndDeletedFalse(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND EXISTS (SELECT 1 FROM u.roles r WHERE r.name = :roleName)")
    List<User> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u WHERE u.deleted = false AND EXISTS (SELECT 1 FROM u.roles r WHERE r.name = :roleName) AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> findByRoleNameAndSearchTerm(@Param("roleName") String roleName, @Param("searchTerm") String searchTerm);
}
