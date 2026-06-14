package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.AttendanceDTO;
import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import com.act.hospitalmanagementsystem.hr.mapper.AttendanceMapper;
import com.act.hospitalmanagementsystem.hr.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            return BaseResponseDTO.success(attendanceMapper.toDTO(saved), "Check-in successful");
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

            // Calculate hours worked
            if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
                double hours = java.time.Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toMinutes() / 60.0;
                attendance.setHoursWorked(hours);
            }

            Attendance saved = attendanceRepository.save(attendance);
            return BaseResponseDTO.success(attendanceMapper.toDTO(saved), "Check-out successful");
        } catch (Exception e) {
            log.error("Error checking out", e);
            return BaseResponseDTO.error("Failed to check out: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<AttendanceDTO>> getAttendance(UUID employeeId, String startDate, String endDate) {
        try {
            List<Attendance> attendances;
            if (startDate != null && endDate != null) {
                attendances = attendanceRepository.findByEmployeeIdAndDateBetween(
                        employeeId, LocalDate.parse(startDate), LocalDate.parse(endDate));
            } else {
                attendances = attendanceRepository.findByEmployeeId(employeeId);
            }
            return BaseResponseDTO.success(attendanceMapper.toDTOList(attendances), "Attendance retrieved");
        } catch (Exception e) {
            log.error("Error getting attendance", e);
            return BaseResponseDTO.error("Failed to get attendance: " + e.getMessage());
        }
    }
}
