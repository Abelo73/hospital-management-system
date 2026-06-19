package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.BranchDTO;
import com.act.hospitalmanagementsystem.hr.entity.Branch;
import com.act.hospitalmanagementsystem.hr.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<BranchDTO>>> getAllBranches(
            @RequestParam(required = false) String status) {
        BaseResponseDTO<List<BranchDTO>> response = branchService.getAllBranches(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<BranchDTO>> getBranchById(@PathVariable UUID id) {
        BaseResponseDTO<BranchDTO> response = branchService.getBranchById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<BranchDTO>> createBranch(
            @RequestBody Branch branch,
            Authentication authentication) {
        BaseResponseDTO<BranchDTO> response = branchService.createBranch(branch, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<BranchDTO>> updateBranch(
            @PathVariable UUID id,
            @RequestBody Branch branch,
            Authentication authentication) {
        BaseResponseDTO<BranchDTO> response = branchService.updateBranch(id, branch, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteBranch(
            @PathVariable UUID id,
            Authentication authentication) {
        BaseResponseDTO<Void> response = branchService.deleteBranch(id, authentication.getName());
        return ResponseEntity.ok(response);
    }
}
