package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.DocumentDTO;
import com.act.hospitalmanagementsystem.auth.dto.DocumentUploadRequest;
import com.act.hospitalmanagementsystem.auth.dto.DocumentVerificationRequest;
import com.act.hospitalmanagementsystem.auth.entity.ApprovalRequest;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.entity.UserDocument;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import com.act.hospitalmanagementsystem.auth.enums.DocumentStatus;
import com.act.hospitalmanagementsystem.auth.repository.ApprovalRequestRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserDocumentRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final UserDocumentRepository userDocumentRepository;
    private final UserRepository userRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    private static final String UPLOAD_DIR = "uploads/documents/";

    public List<DocumentDTO> getUserDocuments(UUID userId) {
        List<UserDocument> documents = userDocumentRepository.findByUserIdAndDeletedFalse(userId);
        return documents.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DocumentDTO getDocument(UUID documentId) {
        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", documentId));
        return mapToDTO(document);
    }

    @Transactional
    public DocumentDTO uploadDocument(UUID userId, DocumentUploadRequest request, MultipartFile file, String uploadedBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if document of same type already exists
        userDocumentRepository.findByUserIdAndDocumentType(userId, request.getDocumentType().name())
                .ifPresent(existing -> {
                    throw new BadRequestException("Document of type " + request.getDocumentType() + " already exists", "DOCUMENT_EXISTS");
                });

        // Save file
        String filePath = saveFile(file, userId);

        UserDocument document = new UserDocument();
        document.setUser(user);
        document.setDocumentType(request.getDocumentType());
        document.setDocumentStatus(DocumentStatus.UPLOADED);
        document.setDocumentName(request.getDocumentName());
        document.setFilePath(filePath);
        document.setFileSize(file.getSize());
        document.setFileType(file.getContentType());
        document.setIssueDate(request.getIssueDate());
        document.setExpiryDate(request.getExpiryDate());
        document.setIssuingAuthority(request.getIssuingAuthority());
        document.setDocumentNumber(request.getDocumentNumber());
        document.setCreatedBy(uploadedBy);

        UserDocument savedDocument = userDocumentRepository.save(document);
        return mapToDTO(savedDocument);
    }

    @Transactional
    public DocumentDTO verifyDocument(UUID documentId, DocumentVerificationRequest verificationRequest, String verifiedBy) {
        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", documentId));

        if (document.getDocumentStatus() != DocumentStatus.UPLOADED &&
            document.getDocumentStatus() != DocumentStatus.PENDING_VERIFICATION) {
            throw new BadRequestException("Cannot verify document in current status", "INVALID_STATUS");
        }

        if (verificationRequest.getVerified()) {
            document.setDocumentStatus(DocumentStatus.VERIFIED);
            document.setVerifiedAt(LocalDateTime.now());
            document.setVerifiedBy(verifiedBy);
            document.setVerificationNotes(verificationRequest.getVerificationNotes());
        } else {
            document.setDocumentStatus(DocumentStatus.REJECTED);
            document.setRejectionReason(verificationRequest.getRejectionReason());
        }

        UserDocument savedDocument = userDocumentRepository.save(document);

        // Check if all required documents are verified
        checkAndUpdateUserApprovalStatus(document.getUser());

        return mapToDTO(savedDocument);
    }

    @Transactional
    public void deleteDocument(UUID documentId) {
        UserDocument document = userDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", documentId));

        // Delete file from filesystem
        try {
            Path path = Paths.get(document.getFilePath());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            // Log error but continue with database deletion
        }

        document.setDeleted(true);
        userDocumentRepository.save(document);
    }

    public List<DocumentDTO> getPendingVerificationDocuments() {
        List<UserDocument> documents = userDocumentRepository.findPendingVerificationDocuments();
        return documents.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DocumentDTO> getExpiringDocuments() {
        List<UserDocument> documents = userDocumentRepository.findExpiredDocuments();
        return documents.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private String saveFile(MultipartFile file, UUID userId) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR + userId);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String filename = UUID.randomUUID().toString() + extension;

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();
        } catch (IOException e) {
            throw new BadRequestException("Failed to save file", "FILE_SAVE_ERROR");
        }
    }

    private void checkAndUpdateUserApprovalStatus(User user) {
        List<UserDocument> userDocuments = userDocumentRepository.findByUserIdAndDeletedFalse(user.getId());
        
        long totalRequired = userDocuments.stream()
                .filter(doc -> doc.getDocumentStatus() != DocumentStatus.PENDING_UPLOAD)
                .count();
        
        long verified = userDocuments.stream()
                .filter(doc -> doc.getDocumentStatus() == DocumentStatus.VERIFIED)
                .count();

        if (totalRequired > 0 && verified == totalRequired) {
            // All documents verified, update approval request
            approvalRequestRepository.findByUserIdAndDeletedFalse(user.getId())
                    .stream()
                    .filter(req -> req.getStatus() == ApprovalStatus.PENDING_VERIFICATION)
                    .findFirst()
                    .ifPresent(req -> {
                        req.setStatus(ApprovalStatus.PENDING_APPROVAL);
                        req.setDocumentsVerified(true);
                        approvalRequestRepository.save(req);
                    });
        }
    }

    private DocumentDTO mapToDTO(UserDocument document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .userId(document.getUser().getId())
                .documentType(document.getDocumentType())
                .documentStatus(document.getDocumentStatus())
                .documentName(document.getDocumentName())
                .filePath(document.getFilePath())
                .fileSize(document.getFileSize())
                .fileType(document.getFileType())
                .issueDate(document.getIssueDate())
                .expiryDate(document.getExpiryDate())
                .issuingAuthority(document.getIssuingAuthority())
                .documentNumber(document.getDocumentNumber())
                .verifiedAt(document.getVerifiedAt())
                .verifiedBy(document.getVerifiedBy())
                .verificationNotes(document.getVerificationNotes())
                .rejectionReason(document.getRejectionReason())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}
