package com.act.hospitalmanagementsystem.auth.dto;

import com.act.hospitalmanagementsystem.auth.enums.DocumentStatus;
import com.act.hospitalmanagementsystem.auth.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private UUID id;
    private UUID userId;
    private DocumentType documentType;
    private DocumentStatus documentStatus;
    private String documentName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issuingAuthority;
    private String documentNumber;
    private LocalDateTime verifiedAt;
    private String verifiedBy;
    private String verificationNotes;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
