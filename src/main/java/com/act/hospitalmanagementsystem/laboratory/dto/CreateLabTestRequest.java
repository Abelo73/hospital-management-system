package com.act.hospitalmanagementsystem.laboratory.dto;

import com.act.hospitalmanagementsystem.laboratory.enums.RequestPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestRequest {
    private UUID patientId;
    private UUID orderingProviderId;
    private RequestPriority priority;
    private String clinicalInformation;
    private List<UUID> testIds;
}
