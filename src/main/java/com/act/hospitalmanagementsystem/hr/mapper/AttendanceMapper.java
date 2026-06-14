package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.AttendanceDTO;
import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceMapper {

    public AttendanceDTO toDTO(Attendance attendance) {
        if (attendance == null) {
            return null;
        }

        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setEmployeeId(attendance.getEmployeeId());
        dto.setDate(attendance.getDate());
        dto.setCheckInTime(attendance.getCheckInTime() != null ? attendance.getCheckInTime().toString() : null);
        dto.setCheckOutTime(attendance.getCheckOutTime() != null ? attendance.getCheckOutTime().toString() : null);
        dto.setStatus(attendance.getStatus());
        dto.setHoursWorked(attendance.getHoursWorked());
        dto.setOvertimeHours(attendance.getOvertimeHours());
        dto.setNotes(attendance.getNotes());
        dto.setCreatedAt(attendance.getCreatedAt());
        dto.setCreatedBy(attendance.getCreatedBy());
        return dto;
    }

    public List<AttendanceDTO> toDTOList(List<Attendance> attendances) {
        return attendances.stream().map(this::toDTO).toList();
    }
}
