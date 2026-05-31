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
public class UserApprovalStatusDTO {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private ApprovalStatus approvalStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private String approvedBy;
    private String rejectionReason;
    private Boolean requiresVerification;
    private Integer pendingDocuments;
    private Integer verifiedDocuments;
}
