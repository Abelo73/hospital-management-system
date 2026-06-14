package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.LeaveRequestDTO;
import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveType;
import com.act.hospitalmanagementsystem.hr.service.LeaveRequestService;
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
@RequestMapping("/api/hr/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<LeaveRequestDTO>> createLeaveRequest(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString((String) request.get("employeeId"));
        LeaveType leaveType = LeaveType.valueOf((String) request.get("leaveType"));
        String startDate = (String) request.get("startDate");
        String endDate = (String) request.get("endDate");
        String reason = (String) request.get("reason");
        String createdBy = authentication.getName();

        BaseResponseDTO<LeaveRequestDTO> response = leaveRequestService.createLeaveRequest(
                employeeId, leaveType, startDate, endDate, reason, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<LeaveRequestDTO>>> getLeaveRequests(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) LeaveStatus status,
            @RequestParam(required = false) LeaveType leaveType,
            Pageable pageable) {
        BaseResponseDTO<List<LeaveRequestDTO>> response = leaveRequestService.getLeaveRequests(employeeId, status, leaveType, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<LeaveRequestDTO>> approveLeaveRequest(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Boolean approved = (Boolean) request.get("approved");
        String rejectionReason = (String) request.get("rejectionReason");
        String approvedBy = authentication.getName();

        BaseResponseDTO<LeaveRequestDTO> response = leaveRequestService.approveLeaveRequest(id, approved, rejectionReason, approvedBy);
        return ResponseEntity.ok(response);
    }
}
