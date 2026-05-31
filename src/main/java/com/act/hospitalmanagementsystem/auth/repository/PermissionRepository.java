package com.act.hospitalmanagementsystem.auth.repository;

import com.act.hospitalmanagementsystem.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM Permission p WHERE p.deleted = false AND p.id = :id")
    Optional<Permission> findByIdAndDeletedFalse(@Param("id") UUID id);

    @Query("SELECT p FROM Permission p WHERE p.deleted = false AND p.name = :name")
    Optional<Permission> findByNameAndDeletedFalse(@Param("name") String name);

    @Query("SELECT p FROM Permission p WHERE p.deleted = false AND p.name IN :names")
    List<Permission> findAllByNameInAndDeletedFalse(@Param("names") Set<String> names);
}
