package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.DepartmentDTO;
import com.act.hospitalmanagementsystem.hr.entity.Department;
import com.act.hospitalmanagementsystem.hr.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<DepartmentDTO>>> getAll(
            @RequestParam(required = false) UUID branchId) {
        return ResponseEntity.ok(departmentService.getAll(branchId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<DepartmentDTO>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<DepartmentDTO>> create(
            @RequestBody Department department,
            Authentication authentication) {
        return ResponseEntity.ok(departmentService.create(department, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<DepartmentDTO>> update(
            @PathVariable UUID id,
            @RequestBody Department department,
            Authentication authentication) {
        return ResponseEntity.ok(departmentService.update(id, department, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> delete(
            @PathVariable UUID id,
            Authentication authentication) {
        return ResponseEntity.ok(departmentService.delete(id, authentication.getName()));
    }
}
