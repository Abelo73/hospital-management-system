package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingTaskDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import com.act.hospitalmanagementsystem.nursing.service.NursingTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/nursing/tasks")
@RequiredArgsConstructor
public class NursingTaskController {

    private final NursingTaskService nursingTaskService;

    @PostMapping
    public ResponseEntity<BaseResponseDTO<NursingTaskDTO>> createNursingTask(@Valid @RequestBody CreateNursingTaskRequest request) {
        NursingTaskDTO task = nursingTaskService.createNursingTask(request);
        return ResponseEntity.ok(BaseResponseDTO.success(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<NursingTaskDTO>> getNursingTaskById(@PathVariable UUID id) {
        NursingTaskDTO task = nursingTaskService.getNursingTaskById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(task));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getNursingTasksByPatientId(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/assigned-to/{assignedTo}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getNursingTasksByAssignedTo(
            @PathVariable UUID assignedTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByAssignedTo(assignedTo, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getNursingTasksByStatus(
            @PathVariable TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getNursingTasksByPriority(
            @PathVariable TaskPriority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByPriority(priority, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/category/{taskCategory}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getNursingTasksByCategory(
            @PathVariable TaskCategory taskCategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByCategory(taskCategory, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/scheduled/{date}")
    public ResponseEntity<BaseResponseDTO<java.util.List<NursingTaskDTO>>> getNursingTasksByScheduledDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        java.util.List<NursingTaskDTO> tasks = nursingTaskService.getNursingTasksByScheduledDate(date);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/overdue/{date}")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> getOverdueTasks(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.getOverdueTasks(date, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseDTO<Page<NursingTaskDTO>>> searchNursingTasks(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingTaskDTO> tasks = nursingTaskService.searchNursingTasks(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(tasks));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<NursingTaskDTO>> updateNursingTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNursingTaskRequest request
    ) {
        NursingTaskDTO task = nursingTaskService.updateNursingTask(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<Void>> deleteNursingTask(@PathVariable UUID id) {
        nursingTaskService.deleteNursingTask(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
