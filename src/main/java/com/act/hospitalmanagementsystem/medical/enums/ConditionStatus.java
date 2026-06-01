package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionStatus {
    ACTIVE("Active"),
    RESOLVED("Resolved"),
    CHRONIC("Chronic"),
    RECURRING("Recurring");

    private final String displayName;
}
