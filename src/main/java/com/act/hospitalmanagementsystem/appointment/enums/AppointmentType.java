package com.act.hospitalmanagementsystem.appointment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppointmentType {
    CONSULTATION("Consultation"),
    FOLLOW_UP("Follow-up"),
    EMERGENCY("Emergency"),
    ROUTINE_CHECKUP("Routine Checkup"),
    SPECIALIST_REFERRAL("Specialist Referral"),
    LAB_TEST("Lab Test"),
    IMAGING("Imaging"),
    THERAPY("Therapy"),
    VACCINATION("Vaccination"),
    TELEHEALTH("Telehealth");

    private final String displayName;
}
