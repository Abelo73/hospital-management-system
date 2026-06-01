package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LabResultStatus {
    NORMAL("Normal"),
    ABNORMAL("Abnormal"),
    CRITICAL("Critical"),
    PENDING("Pending");

    private final String displayName;
}
