package com.act.hospitalmanagementsystem.appointment.dto;

import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import jakarta.validation.constraints.Future;
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
public class UpdateAppointmentRequest {
    private UUID doctorId;
    private AppointmentType appointmentType;
    private AppointmentStatus status;
    
    @Future(message = "Appointment date must be in the future")
    private LocalDate appointmentDate;
    
    private LocalTime startTime;
    private LocalTime endTime;
    
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
    
    private Boolean isVirtual;
    
    @Size(max = 500, message = "Meeting link must not exceed 500 characters")
    private String meetingLink;
    
    private Boolean reminderSent;
}
