package com.act.hospitalmanagementsystem.billing.dto;

import com.act.hospitalmanagementsystem.billing.enums.ClaimStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateClaimStatusRequest {
    @NotNull private ClaimStatus status;
    private BigDecimal approvedAmount;
    private String rejectionReason;
    private String notes;
}
