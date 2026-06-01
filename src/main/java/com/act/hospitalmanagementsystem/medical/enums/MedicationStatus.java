package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicationStatus {
    ACTIVE("Active"),
    DISCONTINUED("Discontinued"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold");

    private final String displayName;
}
