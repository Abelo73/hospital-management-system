package com.act.hospitalmanagementsystem.medical.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordStatus {
    DRAFT("Draft"),
    FINAL("Final"),
    ARCHIVED("Archived");

    private final String displayName;
}
