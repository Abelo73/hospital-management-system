package com.act.hospitalmanagementsystem.patient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PatientStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ADMITTED("Admitted"),
    DISCHARGED("Discharged"),
    DECEASED("Deceased"),
    PENDING_REGISTRATION("Pending Registration");

    private final String displayName;
}
