package com.act.hospitalmanagementsystem.admin.repository;

import com.act.hospitalmanagementsystem.admin.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    Page<AuditLog> findByUserId(UUID userId, Pageable pageable);

    Page<AuditLog> findByAction(String action, Pageable pageable);

    Page<AuditLog> findByEntityType(String entityType, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end")
    Page<AuditLog> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end")
    List<AuditLog> findAllByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT a FROM AuditLog a WHERE (:userId IS NULL OR a.userId = :userId) AND (:action IS NULL OR a.action = :action) AND a.timestamp BETWEEN :start AND :end")
    Page<AuditLog> searchLogs(@Param("userId") UUID userId, @Param("action") String action,
                              @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
}
