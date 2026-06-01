package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.AllergenType;
import com.act.hospitalmanagementsystem.medical.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDTO {
    private UUID id;
    private UUID patientId;
    private AllergenType allergenType;
    private String allergenName;
    private Severity severity;
    private String reaction;
    private LocalDate onsetDate;
    private String reportedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
