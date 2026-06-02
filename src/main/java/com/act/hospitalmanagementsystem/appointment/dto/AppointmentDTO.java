package com.act.hospitalmanagementsystem.appointment.dto;

import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private AppointmentType appointmentType;
    private AppointmentStatus status;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;
    private String reason;
    private String notes;
    private String symptoms;
    private String priority;
    private Boolean isVirtual;
    private String meetingLink;
    private Boolean reminderSent;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
