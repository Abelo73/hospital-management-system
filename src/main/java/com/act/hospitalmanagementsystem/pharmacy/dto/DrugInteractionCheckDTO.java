package com.act.hospitalmanagementsystem.pharmacy.dto;

import com.act.hospitalmanagementsystem.pharmacy.enums.InteractionSeverity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugInteractionCheckDTO {
    private Boolean hasInteractions;
    private List<InteractionDTO> interactions;
    private List<AllergyDTO> allergies;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class InteractionDTO {
    private String drug1;
    private String drug2;
    private InteractionSeverity severity;
    private String description;
    private String management;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AllergyDTO {
    private String drug;
    private String allergy;
    private String severity;
}
