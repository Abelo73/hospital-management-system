package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordType {
    DIAGNOSIS("Diagnosis"),
    PROCEDURE("Procedure"),
    LAB_RESULT("Lab Result"),
    VACCINATION("Vaccination"),
    HOSPITALIZATION("Hospitalization"),
    MEDICATION("Medication");

    private final String displayName;
}
