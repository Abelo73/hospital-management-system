package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.LeaveTypeDTO;
import com.act.hospitalmanagementsystem.hr.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<LeaveTypeDTO>>> getLeaveTypes(
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        return ResponseEntity.ok(leaveTypeService.getAllLeaveTypes(activeOnly));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<LeaveTypeDTO>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveTypeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<LeaveTypeDTO>> create(
            @RequestBody LeaveTypeDTO dto,
            Authentication authentication) {
        return ResponseEntity.ok(leaveTypeService.createLeaveType(dto, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<LeaveTypeDTO>> update(
            @PathVariable UUID id,
            @RequestBody LeaveTypeDTO dto,
            Authentication authentication) {
        return ResponseEntity.ok(leaveTypeService.updateLeaveType(id, dto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveTypeService.deleteLeaveType(id));
    }
}
