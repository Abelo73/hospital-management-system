package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.DocumentDTO;
import com.act.hospitalmanagementsystem.auth.dto.DocumentUploadRequest;
import com.act.hospitalmanagementsystem.auth.dto.DocumentVerificationRequest;
import com.act.hospitalmanagementsystem.auth.service.DocumentService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('DOCUMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<DocumentDTO>>> getUserDocuments(@PathVariable UUID userId) {
        List<DocumentDTO> documents = documentService.getUserDocuments(userId);
        return ResponseEntity.ok(BaseResponseDTO.success(documents));
    }

    @GetMapping("/my-documents")
    public ResponseEntity<BaseResponseDTO<List<DocumentDTO>>> getMyDocuments(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        List<DocumentDTO> documents = documentService.getUserDocuments(userId);
        return ResponseEntity.ok(BaseResponseDTO.success(documents));
    }

    @GetMapping("/{documentId}")
    @PreAuthorize("hasAuthority('DOCUMENT_READ')")
    public ResponseEntity<BaseResponseDTO<DocumentDTO>> getDocument(@PathVariable UUID documentId) {
        DocumentDTO document = documentService.getDocument(documentId);
        return ResponseEntity.ok(BaseResponseDTO.success(document));
    }

    @PostMapping("/upload/{userId}")
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<DocumentDTO>> uploadDocument(
            @PathVariable UUID userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") @Valid DocumentUploadRequest request,
            Authentication authentication) {
        DocumentDTO document = documentService.uploadDocument(userId, request, file, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Document uploaded successfully", document));
    }

    @PostMapping("/upload-my-document")
    public ResponseEntity<BaseResponseDTO<DocumentDTO>> uploadMyDocument(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") @Valid DocumentUploadRequest request,
            Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        DocumentDTO document = documentService.uploadDocument(userId, request, file, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Document uploaded successfully", document));
    }

    @PostMapping("/verify/{documentId}")
    @PreAuthorize("hasAuthority('DOCUMENT_VERIFY')")
    public ResponseEntity<BaseResponseDTO<DocumentDTO>> verifyDocument(
            @PathVariable UUID documentId,
            @Valid @RequestBody DocumentVerificationRequest request,
            Authentication authentication) {
        DocumentDTO document = documentService.verifyDocument(documentId, request, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Document verification processed", document));
    }

    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasAuthority('DOCUMENT_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteDocument(@PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.ok(BaseResponseDTO.success("Document deleted successfully", null));
    }

    @GetMapping("/pending-verification")
    @PreAuthorize("hasAuthority('DOCUMENT_VERIFY')")
    public ResponseEntity<BaseResponseDTO<List<DocumentDTO>>> getPendingVerificationDocuments() {
        List<DocumentDTO> documents = documentService.getPendingVerificationDocuments();
        return ResponseEntity.ok(BaseResponseDTO.success(documents));
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasAuthority('DOCUMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<DocumentDTO>>> getExpiringDocuments() {
        List<DocumentDTO> documents = documentService.getExpiringDocuments();
        return ResponseEntity.ok(BaseResponseDTO.success(documents));
    }
}
