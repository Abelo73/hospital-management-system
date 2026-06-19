package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.SalaryGradeDTO;
import com.act.hospitalmanagementsystem.hr.entity.SalaryGrade;
import com.act.hospitalmanagementsystem.hr.service.SalaryGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/salary-grades")
@RequiredArgsConstructor
public class SalaryGradeController {

    private final SalaryGradeService salaryGradeService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<SalaryGradeDTO>>> getAll() {
        return ResponseEntity.ok(salaryGradeService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<SalaryGradeDTO>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(salaryGradeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<SalaryGradeDTO>> create(
            @RequestBody SalaryGrade grade,
            Authentication authentication) {
        return ResponseEntity.ok(salaryGradeService.create(grade, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<SalaryGradeDTO>> update(
            @PathVariable UUID id,
            @RequestBody SalaryGrade grade,
            Authentication authentication) {
        return ResponseEntity.ok(salaryGradeService.update(id, grade, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> delete(
            @PathVariable UUID id,
            Authentication authentication) {
        return ResponseEntity.ok(salaryGradeService.delete(id, authentication.getName()));
    }
}
