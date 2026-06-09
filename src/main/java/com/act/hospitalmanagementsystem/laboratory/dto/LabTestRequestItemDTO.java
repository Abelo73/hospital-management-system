package com.act.hospitalmanagementsystem.laboratory.dto;

import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.laboratory.enums.ResultFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestRequestItemDTO {
    private UUID id;
    private UUID testId;
    private String testName;
    private RequestStatus status;
    private String resultValue;
    private ResultFlag resultFlag;
    private String unit;
    private String referenceRange;
}
