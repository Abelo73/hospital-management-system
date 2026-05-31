package com.act.hospitalmanagementsystem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitApprovalRequest {
    @NotBlank(message = "Request type is required")
    @Size(max = 50, message = "Request type must not exceed 50 characters")
    private String requestType;

    @Size(max = 50, message = "Requested role must not exceed 50 characters")
    private String requestedRole;

    @NotNull(message = "Priority is required")
    private Integer priority;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
}
