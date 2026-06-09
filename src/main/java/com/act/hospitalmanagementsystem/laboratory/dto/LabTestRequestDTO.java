package com.act.hospitalmanagementsystem.laboratory.dto;

import com.act.hospitalmanagementsystem.laboratory.enums.RequestPriority;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestRequestDTO {
    private UUID id;
    private String requestNumber;
    private UUID patientId;
    private String patientName;
    private UUID orderingProviderId;
    private String orderingProviderName;
    private LocalDate requestDate;
    private LocalTime requestTime;
    private RequestPriority priority;
    private RequestStatus status;
    private String clinicalInformation;
    private List<LabTestRequestItemDTO> items;
    private LocalDateTime resultsReleasedAt;
}
