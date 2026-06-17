package com.act.hospitalmanagementsystem.admin.repository;

import com.act.hospitalmanagementsystem.admin.entity.SystemConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, UUID> {

    Optional<SystemConfig> findByConfigKeyAndDeletedFalse(String configKey);

    List<SystemConfig> findByCategoryAndDeletedFalse(String category);

    Page<SystemConfig> findByDeletedFalse(Pageable pageable);

    boolean existsByConfigKeyAndDeletedFalse(String configKey);
}
