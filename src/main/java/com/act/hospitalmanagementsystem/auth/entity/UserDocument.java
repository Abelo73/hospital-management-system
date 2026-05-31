package com.act.hospitalmanagementsystem.auth.entity;

import com.act.hospitalmanagementsystem.auth.enums.DocumentStatus;
import com.act.hospitalmanagementsystem.auth.enums.DocumentType;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_documents")
public class UserDocument extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 50)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_status", nullable = false, length = 30)
    private DocumentStatus documentStatus = DocumentStatus.PENDING_UPLOAD;

    @Column(name = "document_name", length = 255)
    private String documentName;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type", length = 100)
    private String fileType;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "issuing_authority", length = 255)
    private String issuingAuthority;

    @Column(name = "document_number", length = 100)
    private String documentNumber;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "verified_by", length = 100)
    private String verifiedBy;

    @Column(name = "verification_notes", length = 500)
    private String verificationNotes;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;
}
