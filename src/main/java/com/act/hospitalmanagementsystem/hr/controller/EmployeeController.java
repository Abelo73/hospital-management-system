package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.EmployeeDTO;
import com.act.hospitalmanagementsystem.hr.entity.Employee;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeType;
import com.act.hospitalmanagementsystem.hr.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<EmployeeDTO>>> getEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) EmployeeType employeeType,
            @RequestParam(required = false) EmployeeStatus status,
            @RequestParam(required = false) String query,
            Pageable pageable) {
        BaseResponseDTO<List<EmployeeDTO>> response = employeeService.getEmployees(department, employeeType, status, query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<EmployeeDTO>> getEmployeeById(@PathVariable UUID id) {
        BaseResponseDTO<EmployeeDTO> response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<EmployeeDTO>> createEmployee(
            @RequestBody Employee employee,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<EmployeeDTO> response = employeeService.createEmployee(employee, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<EmployeeDTO>> updateEmployee(
            @PathVariable UUID id,
            @RequestBody Employee employee,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<EmployeeDTO> response = employeeService.updateEmployee(id, employee, updatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/terminate")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> terminateEmployee(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String terminationDate = request.get("terminationDate");
        String reason = request.get("reason");
        String updatedBy = authentication.getName();

        BaseResponseDTO<Void> response = employeeService.terminateEmployee(id, terminationDate, reason, updatedBy);
        return ResponseEntity.ok(response);
    }
}
