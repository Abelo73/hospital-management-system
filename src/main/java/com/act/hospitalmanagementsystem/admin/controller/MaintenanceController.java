package com.act.hospitalmanagementsystem.admin.controller;

import com.act.hospitalmanagementsystem.admin.dto.CreateTaskRequest;
import com.act.hospitalmanagementsystem.admin.dto.ScheduledTaskDTO;
import com.act.hospitalmanagementsystem.admin.service.MaintenanceService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/maintenance")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN_READ')")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/tasks")
    public ResponseEntity<BaseResponseDTO<List<ScheduledTaskDTO>>> getAllTasks() {
        return ResponseEntity.ok(BaseResponseDTO.success(maintenanceService.getAllTasks()));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<BaseResponseDTO<ScheduledTaskDTO>> getTask(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success(maintenanceService.getTaskById(id)));
    }

    @PostMapping("/tasks")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<ScheduledTaskDTO>> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Task created", maintenanceService.createTask(request)));
    }

    @PostMapping("/tasks/{id}/execute")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<ScheduledTaskDTO>> executeTask(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success("Task executed", maintenanceService.executeTask(id)));
    }

    @PatchMapping("/tasks/{id}/enable")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<ScheduledTaskDTO>> enableTask(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success("Task enabled", maintenanceService.toggleTask(id, true)));
    }

    @PatchMapping("/tasks/{id}/disable")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<ScheduledTaskDTO>> disableTask(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success("Task disabled", maintenanceService.toggleTask(id, false)));
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteTask(@PathVariable UUID id) {
        maintenanceService.deleteTask(id);
        return ResponseEntity.ok(BaseResponseDTO.<Void>success("Task deleted", null));
    }

    @PostMapping("/cache/clear")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> clearCache() {
        maintenanceService.clearAllCaches();
        return ResponseEntity.ok(BaseResponseDTO.<Void>success("All caches cleared", null));
    }
}
