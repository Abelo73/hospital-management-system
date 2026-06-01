package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicationRoute {
    ORAL("Oral"),
    INTRAVENOUS("Intravenous"),
    INTRAMUSCULAR("Intramuscular"),
    TOPICAL("Topical"),
    INHALATION("Inhalation"),
    SUBCUTANEOUS("Subcutaneous"),
    RECTAL("Rectal"),
    NASAL("Nasal");

    private final String displayName;
}
