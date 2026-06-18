package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.AttendanceDTO;
import com.act.hospitalmanagementsystem.hr.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hr/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<AttendanceDTO>> checkIn(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString(request.get("employeeId"));
        String checkInTime = request.get("checkInTime");
        String createdBy = authentication.getName();

        BaseResponseDTO<AttendanceDTO> response = attendanceService.checkIn(employeeId, checkInTime, createdBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-out")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<AttendanceDTO>> checkOut(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        UUID attendanceId = UUID.fromString(request.get("attendanceId"));
        String checkOutTime = request.get("checkOutTime");
        String updatedBy = authentication.getName();

        BaseResponseDTO<AttendanceDTO> response = attendanceService.checkOut(attendanceId, checkOutTime, updatedBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<AttendanceDTO>>> getAttendance(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        BaseResponseDTO<List<AttendanceDTO>> response = attendanceService.getAttendance(employeeId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<AttendanceDTO>> createAttendance(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString((String) request.get("employeeId"));
        String date = (String) request.get("date");
        String checkInTime = (String) request.get("checkInTime");
        String checkOutTime = (String) request.get("checkOutTime");
        String status = (String) request.get("status");
        Double hoursWorked = request.get("hoursWorked") != null ? ((Number) request.get("hoursWorked")).doubleValue() : null;
        Double overtimeHours = request.get("overtimeHours") != null ? ((Number) request.get("overtimeHours")).doubleValue() : null;
        String notes = (String) request.get("notes");
        String createdBy = authentication.getName();

        BaseResponseDTO<AttendanceDTO> response = attendanceService.createAttendance(
                employeeId, date, checkInTime, checkOutTime, status, hoursWorked, overtimeHours, notes, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<AttendanceDTO>> updateAttendance(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String checkInTime = (String) request.get("checkInTime");
        String checkOutTime = (String) request.get("checkOutTime");
        String status = (String) request.get("status");
        Double hoursWorked = request.get("hoursWorked") != null ? ((Number) request.get("hoursWorked")).doubleValue() : null;
        Double overtimeHours = request.get("overtimeHours") != null ? ((Number) request.get("overtimeHours")).doubleValue() : null;
        String notes = (String) request.get("notes");
        String updatedBy = authentication.getName();

        BaseResponseDTO<AttendanceDTO> response = attendanceService.updateAttendance(
                id, checkInTime, checkOutTime, status, hoursWorked, overtimeHours, notes, updatedBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<AttendanceDTO>>> getAttendanceByEmployee(
            @PathVariable UUID employeeId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        BaseResponseDTO<List<AttendanceDTO>> response = attendanceService.getAttendance(employeeId, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
