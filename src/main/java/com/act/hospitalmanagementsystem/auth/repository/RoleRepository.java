package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT r FROM Role r WHERE r.deleted = false AND r.id = :id")
    Optional<Role> findByIdAndDeletedFalse(@Param("id") UUID id);

    @Query("SELECT r FROM Role r WHERE r.deleted = false AND r.name = :name")
    Optional<Role> findByNameAndDeletedFalse(@Param("name") String name);
}
