package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.ApprovalActionRequest;
import com.act.hospitalmanagementsystem.auth.dto.ApprovalRequestDTO;
import com.act.hospitalmanagementsystem.auth.dto.SubmitApprovalRequest;
import com.act.hospitalmanagementsystem.auth.dto.UserApprovalStatusDTO;
import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.service.ApprovalService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;
    private final com.act.hospitalmanagementsystem.auth.repository.UserRepository userRepository;

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<List<ApprovalRequestDTO>>> getPendingApprovals() {
        List<ApprovalRequestDTO> approvals = approvalService.getPendingApprovals();
        return ResponseEntity.ok(BaseResponseDTO.success(approvals));
    }

    @GetMapping("/pending-verification")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<List<ApprovalRequestDTO>>> getPendingVerifications() {
        List<ApprovalRequestDTO> approvals = approvalService.getPendingVerifications();
        return ResponseEntity.ok(BaseResponseDTO.success(approvals));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<List<ApprovalRequestDTO>>> getAllApprovalRequests() {
        List<ApprovalRequestDTO> approvals = approvalService.getAllApprovalRequests();
        return ResponseEntity.ok(BaseResponseDTO.success(approvals));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<List<ApprovalRequestDTO>>> getUserApprovalRequests(@PathVariable UUID userId) {
        List<ApprovalRequestDTO> approvals = approvalService.getUserApprovalRequests(userId);
        return ResponseEntity.ok(BaseResponseDTO.success(approvals));
    }

    @GetMapping("/request/{requestId}")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<ApprovalRequestDTO>> getApprovalRequest(@PathVariable UUID requestId) {
        ApprovalRequestDTO approval = approvalService.getApprovalRequest(requestId);
        return ResponseEntity.ok(BaseResponseDTO.success(approval));
    }

    @PostMapping("/submit/{userId}")
    @PreAuthorize("hasAuthority('APPROVAL_WRITE')")
    public ResponseEntity<BaseResponseDTO<ApprovalRequestDTO>> submitApprovalRequest(
            @PathVariable UUID userId,
            @Valid @RequestBody SubmitApprovalRequest request,
            Authentication authentication) {
        ApprovalRequestDTO approval = approvalService.submitApprovalRequest(userId, request, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Approval request submitted successfully", approval));
    }

    @PostMapping("/process/{requestId}")
    @PreAuthorize("hasAuthority('APPROVAL_WRITE')")
    public ResponseEntity<BaseResponseDTO<ApprovalRequestDTO>> processApprovalRequest(
            @PathVariable UUID requestId,
            @Valid @RequestBody ApprovalActionRequest request,
            Authentication authentication) {
        ApprovalRequestDTO approval = approvalService.processApprovalRequest(requestId, request, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Approval request processed successfully", approval));
    }

    @PostMapping("/resubmit/{userId}")
    @PreAuthorize("hasAuthority('APPROVAL_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> resubmitApprovalRequest(
            @PathVariable UUID userId,
            Authentication authentication) {
        approvalService.resubmitApprovalRequest(userId, authentication.getName());
        return ResponseEntity.ok(BaseResponseDTO.success("Approval request resubmitted successfully", null));
    }

    @GetMapping("/status/{userId}")
    @PreAuthorize("hasAuthority('APPROVAL_READ')")
    public ResponseEntity<BaseResponseDTO<UserApprovalStatusDTO>> getUserApprovalStatus(@PathVariable UUID userId) {
        UserApprovalStatusDTO status = approvalService.getUserApprovalStatus(userId);
        return ResponseEntity.ok(BaseResponseDTO.success(status));
    }

    @GetMapping("/my-status")
    public ResponseEntity<BaseResponseDTO<UserApprovalStatusDTO>> getMyApprovalStatus(Authentication authentication) {
        User user = userRepository.findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() -> new com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException("User", "username", authentication.getName()));
        UserApprovalStatusDTO status = approvalService.getUserApprovalStatus(user.getId());
        return ResponseEntity.ok(BaseResponseDTO.success(status));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<BaseResponseDTO<List<ApprovalRequestDTO>>> getMyApprovalRequests(Authentication authentication) {
        User user = userRepository.findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() -> new com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException("User", "username", authentication.getName()));
        List<ApprovalRequestDTO> approvals = approvalService.getUserApprovalRequests(user.getId());
        return ResponseEntity.ok(BaseResponseDTO.success(approvals));
    }
}
