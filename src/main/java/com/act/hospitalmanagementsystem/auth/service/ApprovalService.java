package com.act.hospitalmanagementsystem.auth.service;

import com.act.hospitalmanagementsystem.auth.dto.ApprovalActionRequest;
import com.act.hospitalmanagementsystem.auth.dto.ApprovalRequestDTO;
import com.act.hospitalmanagementsystem.auth.dto.SubmitApprovalRequest;
import com.act.hospitalmanagementsystem.auth.dto.UserApprovalStatusDTO;
import com.act.hospitalmanagementsystem.auth.entity.ApprovalRequest;
import com.act.hospitalmanagementsystem.auth.entity.Role;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.enums.ApprovalStatus;
import com.act.hospitalmanagementsystem.auth.repository.ApprovalRequestRepository;
import com.act.hospitalmanagementsystem.auth.repository.RoleRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserDocumentRepository;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDocumentRepository userDocumentRepository;

    @Transactional
    public List<ApprovalRequestDTO> getPendingApprovals() {
        List<ApprovalRequest> requests = approvalRequestRepository.findByStatus(ApprovalStatus.PENDING_APPROVAL);
        
        // Also include users with PENDING_APPROVAL or PENDING_SUBMISSION status who don't have approval requests
        List<User> usersWithPendingApproval = userRepository.findAll().stream()
                .filter(user -> user.getApprovalStatus() == ApprovalStatus.PENDING_APPROVAL || 
                              user.getApprovalStatus() == ApprovalStatus.PENDING_SUBMISSION)
                .filter(user -> requests.stream().noneMatch(req -> req.getUser() != null && req.getUser().getId().equals(user.getId())))
                .collect(Collectors.toList());
        
        // Create approval request entities for users without requests
        for (User user : usersWithPendingApproval) {
            ApprovalRequest approvalRequest = new ApprovalRequest();
            approvalRequest.setUser(user);
            approvalRequest.setRequestType("USER_CREATION");
            approvalRequest.setRequestedRole(user.getRoles().stream().map(Role::getName).findFirst().orElse(null));
            approvalRequest.setStatus(ApprovalStatus.PENDING_APPROVAL);
            approvalRequest.setPriority(5);
            approvalRequest.setSubmittedAt(user.getSubmittedAt() != null ? user.getSubmittedAt() : user.getCreatedAt());
            approvalRequest.setSubmittedBy("SYSTEM");
            approvalRequest.setDocumentsRequired(false);
            approvalRequest.setDocumentsVerified(true);
            approvalRequest = approvalRequestRepository.save(approvalRequest);
            requests.add(approvalRequest);
            
            // Update user status to PENDING_APPROVAL if it was PENDING_SUBMISSION
            if (user.getApprovalStatus() == ApprovalStatus.PENDING_SUBMISSION) {
                user.setApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
                user.setSubmittedAt(LocalDateTime.now());
                userRepository.save(user);
            }
        }
        
        return requests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ApprovalRequestDTO> getPendingVerifications() {
        List<ApprovalRequest> requests = approvalRequestRepository.findByStatus(ApprovalStatus.PENDING_VERIFICATION);
        return requests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ApprovalRequestDTO> getAllApprovalRequests() {
        List<ApprovalRequest> requests = approvalRequestRepository.findAll();
        return requests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ApprovalRequestDTO> getUserApprovalRequests(UUID userId) {
        List<ApprovalRequest> requests = approvalRequestRepository.findByUserIdAndDeletedFalse(userId);
        return requests.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ApprovalRequestDTO getApprovalRequest(UUID requestId) {
        ApprovalRequest request = approvalRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval request", "id", requestId));
        return mapToDTO(request);
    }

    @Transactional
    public ApprovalRequestDTO submitApprovalRequest(UUID userId, SubmitApprovalRequest submitRequest, String submittedBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if there's already a pending request
        boolean hasPendingRequest = approvalRequestRepository.findByUserIdAndDeletedFalse(userId)
                .stream()
                .anyMatch(req -> req.getStatus() == ApprovalStatus.PENDING_SUBMISSION ||
                                req.getStatus() == ApprovalStatus.PENDING_VERIFICATION ||
                                req.getStatus() == ApprovalStatus.PENDING_APPROVAL);

        if (hasPendingRequest) {
            throw new BadRequestException("User already has a pending approval request", "PENDING_REQUEST_EXISTS");
        }

        // Determine if documents are required based on role
        boolean documentsRequired = requiresDocumentVerification(submitRequest.getRequestedRole());

        ApprovalRequest approvalRequest = new ApprovalRequest();
        approvalRequest.setUser(user);
        approvalRequest.setRequestType(submitRequest.getRequestType());
        approvalRequest.setRequestedRole(submitRequest.getRequestedRole());
        approvalRequest.setStatus(documentsRequired ? ApprovalStatus.PENDING_VERIFICATION : ApprovalStatus.PENDING_APPROVAL);
        approvalRequest.setPriority(submitRequest.getPriority());
        approvalRequest.setSubmittedAt(LocalDateTime.now());
        approvalRequest.setSubmittedBy(submittedBy);
        approvalRequest.setNotes(submitRequest.getNotes());
        approvalRequest.setDocumentsRequired(documentsRequired);
        approvalRequest.setDocumentsVerified(!documentsRequired);

        // Update user approval status
        user.setApprovalStatus(documentsRequired ? ApprovalStatus.PENDING_VERIFICATION : ApprovalStatus.PENDING_APPROVAL);
        user.setSubmittedAt(LocalDateTime.now());
        user.setRequiresVerification(documentsRequired);
        userRepository.save(user);

        ApprovalRequest savedRequest = approvalRequestRepository.save(approvalRequest);
        return mapToDTO(savedRequest);
    }

    @Transactional
    public ApprovalRequestDTO processApprovalRequest(UUID requestId, ApprovalActionRequest actionRequest, String processedBy) {
        ApprovalRequest request = approvalRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval request", "id", requestId));

        User user = request.getUser();
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", requestId);
        }

        switch (actionRequest.getAction().toUpperCase()) {
            case "APPROVE":
                if (!request.getDocumentsVerified() && request.getDocumentsRequired()) {
                    throw new BadRequestException("Cannot approve request until documents are verified", "DOCUMENTS_NOT_VERIFIED");
                }
                request.setStatus(ApprovalStatus.APPROVED);
                request.setApprovedAt(LocalDateTime.now());
                request.setApprovedBy(processedBy);
                request.setReviewedAt(LocalDateTime.now());
                request.setReviewedBy(processedBy);

                user.setApprovalStatus(ApprovalStatus.APPROVED);
                user.setApprovedAt(LocalDateTime.now());
                user.setApprovedBy(processedBy);
                user.setEnabled(true);

                // Assign the requested role if specified
                if (request.getRequestedRole() != null) {
                    Role role = roleRepository.findByNameAndDeletedFalse(request.getRequestedRole())
                            .orElseThrow(() -> new ResourceNotFoundException("Role", "name", request.getRequestedRole()));
                    if (!user.getRoles().contains(role)) {
                        user.addRole(role);
                    }
                }
                userRepository.save(user);
                break;

            case "REJECT":
                request.setStatus(ApprovalStatus.REJECTED);
                request.setRejectionReason(actionRequest.getReason());
                request.setReviewedAt(LocalDateTime.now());
                request.setReviewedBy(processedBy);

                user.setApprovalStatus(ApprovalStatus.REJECTED);
                user.setRejectionReason(actionRequest.getReason());
                user.setEnabled(false);
                userRepository.save(user);
                break;

            case "REQUEST_INFO":
                request.setStatus(ApprovalStatus.PENDING_SUBMISSION);
                request.setNotes(actionRequest.getNotes());
                request.setReviewedAt(LocalDateTime.now());
                request.setReviewedBy(processedBy);

                user.setApprovalStatus(ApprovalStatus.PENDING_SUBMISSION);
                userRepository.save(user);
                break;

            default:
                throw new BadRequestException("Invalid action: " + actionRequest.getAction(), "INVALID_ACTION");
        }

        ApprovalRequest savedRequest = approvalRequestRepository.save(request);
        return mapToDTO(savedRequest);
    }

    public UserApprovalStatusDTO getUserApprovalStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        long pendingDocuments = userDocumentRepository.findByUserIdAndDeletedFalse(userId)
                .stream()
                .filter(doc -> doc.getDocumentStatus().name().startsWith("PENDING"))
                .count();

        long verifiedDocuments = userDocumentRepository.findByUserIdAndDeletedFalse(userId)
                .stream()
                .filter(doc -> doc.getDocumentStatus().name().equals("VERIFIED"))
                .count();

        return UserApprovalStatusDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .approvalStatus(user.getApprovalStatus())
                .submittedAt(user.getSubmittedAt())
                .approvedAt(user.getApprovedAt())
                .approvedBy(user.getApprovedBy())
                .rejectionReason(user.getRejectionReason())
                .requiresVerification(user.getRequiresVerification())
                .pendingDocuments((int) pendingDocuments)
                .verifiedDocuments((int) verifiedDocuments)
                .build();
    }

    @Transactional
    public void resubmitApprovalRequest(UUID userId, String submittedBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getApprovalStatus() != ApprovalStatus.REJECTED &&
            user.getApprovalStatus() != ApprovalStatus.PENDING_SUBMISSION) {
            throw new BadRequestException("Cannot resubmit request in current status", "INVALID_STATUS");
        }

        ApprovalRequest latestRequest = approvalRequestRepository.findByUserIdAndDeletedFalse(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No approval request found for user"));

        latestRequest.setStatus(ApprovalStatus.PENDING_VERIFICATION);
        latestRequest.setSubmittedAt(LocalDateTime.now());
        latestRequest.setSubmittedBy(submittedBy);
        latestRequest.setRejectionReason(null);

        user.setApprovalStatus(ApprovalStatus.PENDING_VERIFICATION);
        user.setSubmittedAt(LocalDateTime.now());
        user.setRejectionReason(null);

        approvalRequestRepository.save(latestRequest);
        userRepository.save(user);
    }

    private boolean requiresDocumentVerification(String roleName) {
        if (roleName == null) {
            return false;
        }
        return roleName.equals("DOCTOR") ||
               roleName.equals("NURSE") ||
               roleName.equals("PHARMACIST") ||
               roleName.equals("LAB_TECHNICIAN");
    }

    private ApprovalRequestDTO mapToDTO(ApprovalRequest request) {
        User user = request.getUser();
        return ApprovalRequestDTO.builder()
                .id(request.getId())
                .userId(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .requestType(request.getRequestType())
                .requestedRole(request.getRequestedRole())
                .status(request.getStatus())
                .priority(request.getPriority())
                .submittedAt(request.getSubmittedAt())
                .submittedBy(request.getSubmittedBy())
                .reviewedAt(request.getReviewedAt())
                .reviewedBy(request.getReviewedBy())
                .approvedAt(request.getApprovedAt())
                .approvedBy(request.getApprovedBy())
                .rejectionReason(request.getRejectionReason())
                .notes(request.getNotes())
                .documentsRequired(request.getDocumentsRequired())
                .documentsVerified(request.getDocumentsVerified())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
