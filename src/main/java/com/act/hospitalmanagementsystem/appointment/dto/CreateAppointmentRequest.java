package com.act.hospitalmanagementsystem.appointment.dto;

import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {
    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;

    @NotNull(message = "Appointment type is required")
    private AppointmentType appointmentType;

    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDate appointmentDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationMinutes;

    @Size(max = 500, message = "Reason must not exceed 500 characters")
    private String reason;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;

    @Size(max = 1000, message = "Symptoms must not exceed 1000 characters")
    private String symptoms;

    @Size(max = 20, message = "Priority must not exceed 20 characters")
    private String priority;

    private Boolean isVirtual = false;

    @Size(max = 500, message = "Meeting link must not exceed 500 characters")
    private String meetingLink;
}
