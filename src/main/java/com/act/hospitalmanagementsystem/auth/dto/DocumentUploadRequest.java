package com.act.hospitalmanagementsystem.auth.dto;

import com.act.hospitalmanagementsystem.auth.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {
    @NotNull(message = "Document type is required")
    private DocumentType documentType;

    @NotBlank(message = "Document name is required")
    @Size(max = 255, message = "Document name must not exceed 255 characters")
    private String documentName;

    @Size(max = 100, message = "File type must not exceed 100 characters")
    private String fileType;

    @Past(message = "Issue date must be in the past")
    private LocalDate issueDate;

    private LocalDate expiryDate;

    @Size(max = 255, message = "Issuing authority must not exceed 255 characters")
    private String issuingAuthority;

    @Size(max = 100, message = "Document number must not exceed 100 characters")
    private String documentNumber;
}
