package com.act.hospitalmanagementsystem.appointment.entity;

import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Appointment extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false, length = 50)
    private AppointmentType appointmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private AppointmentStatus status;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "symptoms", length = 1000)
    private String symptoms;

    @Column(name = "priority", length = 20)
    private String priority;

    @Column(name = "is_virtual", nullable = false)
    private Boolean isVirtual = false;

    @Column(name = "meeting_link", length = 500)
    private String meetingLink;

    @Column(name = "reminder_sent", nullable = false)
    private Boolean reminderSent = false;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;
}
