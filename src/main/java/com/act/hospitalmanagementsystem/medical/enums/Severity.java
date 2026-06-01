package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Severity {
    MILD("Mild"),
    MODERATE("Moderate"),
    SEVERE("Severe"),
    LIFE_THREATENING("Life-threatening");

    private final String displayName;
}
