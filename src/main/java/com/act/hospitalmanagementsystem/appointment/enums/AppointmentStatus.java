package com.act.hospitalmanagementsystem.appointment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppointmentStatus {
    SCHEDULED("Scheduled"),
    CONFIRMED("Confirmed"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    NO_SHOW("No Show"),
    RESCHEDULED("Rescheduled");

    private final String displayName;
}
