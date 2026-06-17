package com.act.hospitalmanagementsystem.admin.service;

import com.act.hospitalmanagementsystem.admin.dto.AuditLogDTO;
import com.act.hospitalmanagementsystem.admin.entity.AuditLog;
import com.act.hospitalmanagementsystem.admin.mapper.AdminMapper;
import com.act.hospitalmanagementsystem.admin.repository.AuditLogRepository;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AdminMapper adminMapper;

    @Async
    public void log(String username, UUID userId, String action, String entityType,
                    String entityId, String ipAddress, String requestUrl, String requestMethod,
                    Integer responseStatus, Long duration, String errorMessage) {
        try {
            AuditLog entry = new AuditLog();
            entry.setUsername(username);
            entry.setUserId(userId);
            entry.setAction(action);
            entry.setEntityType(entityType);
            entry.setEntityId(entityId);
            entry.setIpAddress(ipAddress);
            entry.setRequestUrl(requestUrl);
            entry.setRequestMethod(requestMethod);
            entry.setResponseStatus(responseStatus);
            entry.setDuration(duration);
            entry.setErrorMessage(errorMessage);
            entry.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(entry);
        } catch (Exception e) {
            log.error("Failed to save audit log", e);
        }
    }

    public Page<AuditLogDTO> getAuditLogs(UUID userId, String action,
                                           LocalDateTime startDate, LocalDateTime endDate,
                                           Pageable pageable) {
        LocalDateTime start = startDate != null ? startDate : LocalDateTime.now().minusDays(30);
        LocalDateTime end = endDate != null ? endDate : LocalDateTime.now();
        return auditLogRepository.searchLogs(userId, action, start, end, pageable)
                .map(adminMapper::toDTO);
    }

    public AuditLogDTO getById(UUID id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", "id", id));
        return adminMapper.toDTO(log);
    }

    public List<AuditLogDTO> exportLogs(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime start = startDate != null ? startDate : LocalDateTime.now().minusDays(30);
        LocalDateTime end = endDate != null ? endDate : LocalDateTime.now();
        return auditLogRepository.findAllByDateRange(start, end)
                .stream().map(adminMapper::toDTO).collect(Collectors.toList());
    }
}
