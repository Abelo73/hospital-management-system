package com.act.hospitalmanagementsystem.auth.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVerificationRequest {
    private Boolean verified;

    @Size(max = 500, message = "Verification notes must not exceed 500 characters")
    private String verificationNotes;

    @Size(max = 500, message = "Rejection reason must not exceed 500 characters")
    private String rejectionReason;
}
