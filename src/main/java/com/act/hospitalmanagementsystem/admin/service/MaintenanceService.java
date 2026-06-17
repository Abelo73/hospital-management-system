package com.act.hospitalmanagementsystem.admin.service;

import com.act.hospitalmanagementsystem.admin.dto.CreateTaskRequest;
import com.act.hospitalmanagementsystem.admin.dto.ScheduledTaskDTO;
import com.act.hospitalmanagementsystem.admin.entity.ScheduledTask;
import com.act.hospitalmanagementsystem.admin.enums.TaskStatus;
import com.act.hospitalmanagementsystem.admin.mapper.AdminMapper;
import com.act.hospitalmanagementsystem.admin.repository.ScheduledTaskRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final ScheduledTaskRepository scheduledTaskRepository;
    private final AdminMapper adminMapper;
    private final CacheManager cacheManager;

    public List<ScheduledTaskDTO> getAllTasks() {
        return scheduledTaskRepository.findAll().stream()
                .filter(t -> !Boolean.TRUE.equals(t.getDeleted()))
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ScheduledTaskDTO getTaskById(UUID id) {
        ScheduledTask task = scheduledTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScheduledTask", "id", id));
        return adminMapper.toDTO(task);
    }

    @Transactional
    public ScheduledTaskDTO createTask(CreateTaskRequest request) {
        if (scheduledTaskRepository.existsByTaskNameAndDeletedFalse(request.getTaskName())) {
            throw new BadRequestException("Task with name '" + request.getTaskName() + "' already exists", "TASK_EXISTS");
        }
        ScheduledTask task = new ScheduledTask();
        task.setTaskName(request.getTaskName());
        task.setTaskType(request.getTaskType());
        task.setCronExpression(request.getCronExpression());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.ACTIVE);
        task.setEnabled(true);
        task.setFailureCount(0);
        return adminMapper.toDTO(scheduledTaskRepository.save(task));
    }

    @Transactional
    public ScheduledTaskDTO executeTask(UUID id) {
        ScheduledTask task = scheduledTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScheduledTask", "id", id));
        task.setStatus(TaskStatus.RUNNING);
        long start = System.currentTimeMillis();
        try {
            log.info("Manually executing task: {}", task.getTaskName());
            // Task execution logic varies by type — log and mark complete
            Thread.sleep(100);
            task.setStatus(TaskStatus.COMPLETED);
            task.setLastRunStatus("SUCCESS");
            task.setLastRunMessage("Manual execution completed");
            task.setFailureCount(0);
        } catch (Exception e) {
            task.setStatus(TaskStatus.FAILED);
            task.setLastRunStatus("FAILED");
            task.setLastRunMessage(e.getMessage());
            task.setFailureCount(task.getFailureCount() + 1);
        }
        task.setLastRunAt(LocalDateTime.now());
        task.setLastRunDuration(System.currentTimeMillis() - start);
        return adminMapper.toDTO(scheduledTaskRepository.save(task));
    }

    @Transactional
    public ScheduledTaskDTO toggleTask(UUID id, boolean enabled) {
        ScheduledTask task = scheduledTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScheduledTask", "id", id));
        task.setEnabled(enabled);
        task.setStatus(enabled ? TaskStatus.ACTIVE : TaskStatus.DISABLED);
        return adminMapper.toDTO(scheduledTaskRepository.save(task));
    }

    @Transactional
    public void deleteTask(UUID id) {
        ScheduledTask task = scheduledTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScheduledTask", "id", id));
        task.setDeleted(true);
        scheduledTaskRepository.save(task);
    }

    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(name -> {
            var cache = cacheManager.getCache(name);
            if (cache != null) cache.clear();
        });
        log.info("All caches cleared");
    }
}
