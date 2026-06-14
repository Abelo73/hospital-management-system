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
@RequestMapping("/api/hr/attendance")
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
}
