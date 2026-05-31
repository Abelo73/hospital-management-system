package com.act.hospitalmanagementsystem.auth.dto;

import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequestDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String requestType;
    private String requestedRole;
    private ApprovalStatus status;
    private Integer priority;
    private LocalDateTime submittedAt;
    private String submittedBy;
    private LocalDateTime reviewedAt;
    private String reviewedBy;
    private LocalDateTime approvedAt;
    private String approvedBy;
    private String rejectionReason;
    private String notes;
    private Boolean documentsRequired;
    private Boolean documentsVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
