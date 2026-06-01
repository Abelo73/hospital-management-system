package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiagnosisType {
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    ADMITTING("Admitting");

    private final String displayName;
}
