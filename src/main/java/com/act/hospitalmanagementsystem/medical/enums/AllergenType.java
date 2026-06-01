package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AllergenType {
    DRUG("Drug"),
    FOOD("Food"),
    ENVIRONMENT("Environmental");

    private final String displayName;
}
