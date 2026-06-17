package com.act.hospitalmanagementsystem.billing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class SubmitClaimRequest {
    @NotNull private UUID invoiceId;
    @NotNull private UUID providerId;
    @NotNull private UUID patientId;
    private String notes;
}
