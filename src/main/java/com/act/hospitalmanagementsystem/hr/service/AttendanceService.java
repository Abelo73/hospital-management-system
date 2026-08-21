package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.AttendanceDTO;
import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import com.act.hospitalmanagementsystem.hr.mapper.AttendanceMapper;
import com.act.hospitalmanagementsystem.hr.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Transactional
    public BaseResponseDTO<AttendanceDTO> checkIn(UUID employeeId, String checkInTime, String createdBy) {
        try {
            Attendance attendance = new Attendance();
            attendance.setEmployeeId(employeeId);
            attendance.setDate(LocalDate.now());
            attendance.setCheckInTime(LocalTime.parse(checkInTime));
            attendance.setStatus(AttendanceStatus.PRESENT);
            attendance.setCreatedBy(createdBy);

            Attendance saved = attendanceRepository.save(attendance);
            return BaseResponseDTO.success("Check-in successful", attendanceMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error checking in", e);
            return BaseResponseDTO.error("Failed to check in: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<AttendanceDTO> checkOut(UUID attendanceId, String checkOutTime, String updatedBy) {
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new RuntimeException("Attendance record not found"));

            attendance.setCheckOutTime(LocalTime.parse(checkOutTime));
            attendance.setUpdatedBy(updatedBy);

            if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
                double hours = java.time.Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toMinutes() / 60.0;
                attendance.setHoursWorked(hours);
            }

            Attendance saved = attendanceRepository.save(attendance);
            return BaseResponseDTO.success("Check-out successful", attendanceMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error checking out", e);
            return BaseResponseDTO.error("Failed to check out: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<AttendanceDTO>> getAttendance(UUID employeeId, String startDate, String endDate, Pageable pageable) {
        try {
            Page<Attendance> attendances;
            if (employeeId != null && startDate != null && endDate != null) {
                attendances = attendanceRepository.findByEmployeeIdAndDateBetween(
                        employeeId, LocalDate.parse(startDate), LocalDate.parse(endDate), pageable);
            } else if (employeeId != null) {
                attendances = attendanceRepository.findByEmployeeId(employeeId, pageable);
            } else {
                attendances = attendanceRepository.findAll(pageable);
            }
            return BaseResponseDTO.success("Attendance retrieved", attendanceMapper.toDTOList(attendances.getContent()));
        } catch (Exception e) {
            log.error("Error getting attendance", e);
            return BaseResponseDTO.error("Failed to get attendance: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<AttendanceDTO> createAttendance(UUID employeeId, String date, String checkInTime,
            String checkOutTime, String status, Double hoursWorked, Double overtimeHours, String notes, String createdBy) {
        try {
            Attendance attendance = new Attendance();
            attendance.setEmployeeId(employeeId);
            attendance.setDate(LocalDate.parse(date));
            if (checkInTime != null) attendance.setCheckInTime(LocalTime.parse(checkInTime));
            if (checkOutTime != null) attendance.setCheckOutTime(LocalTime.parse(checkOutTime));
            attendance.setStatus(status != null ? AttendanceStatus.valueOf(status) : AttendanceStatus.PRESENT);
            attendance.setHoursWorked(hoursWorked);
            attendance.setOvertimeHours(overtimeHours);
            attendance.setNotes(notes);
            attendance.setCreatedBy(createdBy);

            Attendance saved = attendanceRepository.save(attendance);
            return BaseResponseDTO.success("Attendance record created", attendanceMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating attendance", e);
            return BaseResponseDTO.error("Failed to create attendance: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<AttendanceDTO> updateAttendance(UUID id, String checkInTime, String checkOutTime,
            String status, Double hoursWorked, Double overtimeHours, String notes, String updatedBy) {
        try {
            Attendance attendance = attendanceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Attendance record not found"));

            if (checkInTime != null) attendance.setCheckInTime(LocalTime.parse(checkInTime));
            if (checkOutTime != null) {
                attendance.setCheckOutTime(LocalTime.parse(checkOutTime));
                if (attendance.getCheckInTime() != null) {
                    double hours = java.time.Duration.between(attendance.getCheckInTime(), LocalTime.parse(checkOutTime)).toMinutes() / 60.0;
                    attendance.setHoursWorked(hours);
                }
            }
            if (status != null) attendance.setStatus(AttendanceStatus.valueOf(status));
            if (hoursWorked != null) attendance.setHoursWorked(hoursWorked);
            if (overtimeHours != null) attendance.setOvertimeHours(overtimeHours);
            if (notes != null) attendance.setNotes(notes);
            attendance.setUpdatedBy(updatedBy);

            Attendance saved = attendanceRepository.save(attendance);
            return BaseResponseDTO.success("Attendance record updated", attendanceMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error updating attendance", e);
            return BaseResponseDTO.error("Failed to update attendance: " + e.getMessage());
        }
    }
}
