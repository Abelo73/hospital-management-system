package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.BranchDTO;
import com.act.hospitalmanagementsystem.hr.entity.Branch;
import com.act.hospitalmanagementsystem.hr.mapper.BranchMapper;
import com.act.hospitalmanagementsystem.hr.repository.BranchRepository;
import com.act.hospitalmanagementsystem.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchMapper branchMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<BranchDTO>> getAllBranches(String status) {
        try {
            List<Branch> branches = status != null
                    ? branchRepository.findByStatusAndDeletedFalse(status)
                    : branchRepository.findByDeletedFalseOrderByNameAsc();
            return BaseResponseDTO.success("Branches retrieved", branchMapper.toDTOList(branches));
        } catch (Exception e) {
            log.error("Error retrieving branches", e);
            return BaseResponseDTO.error("Failed to retrieve branches: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<BranchDTO> getBranchById(UUID id) {
        try {
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            return BaseResponseDTO.success("Branch retrieved", branchMapper.toDTO(branch));
        } catch (Exception e) {
            log.error("Error retrieving branch {}", id, e);
            return BaseResponseDTO.error("Branch not found: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<BranchDTO> createBranch(Branch branch, String createdBy) {
        try {
            // Auto-generate code if not supplied
            if (branch.getCode() == null || branch.getCode().isBlank()) {
                branch.setCode(generateBranchCode(branch.getName()));
            }
            if (branchRepository.existsByCodeAndDeletedFalse(branch.getCode())) {
                return BaseResponseDTO.error("Branch code already exists: " + branch.getCode());
            }
            branch.setCreatedBy(createdBy);
            branch.setDeleted(false);
            Branch saved = branchRepository.save(branch);
            return BaseResponseDTO.success("Branch created successfully", branchMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating branch", e);
            return BaseResponseDTO.error("Failed to create branch: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<BranchDTO> updateBranch(UUID id, Branch updates, String updatedBy) {
        try {
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            if (updates.getName() != null) branch.setName(updates.getName());
            if (updates.getAddress() != null) branch.setAddress(updates.getAddress());
            if (updates.getCity() != null) branch.setCity(updates.getCity());
            if (updates.getCountry() != null) branch.setCountry(updates.getCountry());
            if (updates.getPhone() != null) branch.setPhone(updates.getPhone());
            if (updates.getEmail() != null) branch.setEmail(updates.getEmail());
            if (updates.getBranchType() != null) branch.setBranchType(updates.getBranchType());
            if (updates.getParentBranchId() != null) branch.setParentBranchId(updates.getParentBranchId());
            if (updates.getStatus() != null) branch.setStatus(updates.getStatus());
            branch.setUpdatedBy(updatedBy);
            return BaseResponseDTO.success("Branch updated", branchMapper.toDTO(branchRepository.save(branch)));
        } catch (Exception e) {
            log.error("Error updating branch {}", id, e);
            return BaseResponseDTO.error("Failed to update branch: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteBranch(UUID id, String updatedBy) {
        try {
            Branch branch = branchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            long activeEmployees = employeeRepository.countByBranchIdAndDeletedFalse(id);
            if (activeEmployees > 0) {
                return BaseResponseDTO.error("Cannot delete branch: " + activeEmployees + " active employee(s) assigned");
            }
            branch.setDeleted(true);
            branch.setStatus("INACTIVE");
            branch.setUpdatedBy(updatedBy);
            branchRepository.save(branch);
            return BaseResponseDTO.success("Branch deleted", null);
        } catch (Exception e) {
            log.error("Error deleting branch {}", id, e);
            return BaseResponseDTO.error("Failed to delete branch: " + e.getMessage());
        }
    }

    private String generateBranchCode(String name) {
        String base = name.toUpperCase().replaceAll("[^A-Z0-9]", "").substring(0, Math.min(4, name.length()));
        return base + "-" + System.currentTimeMillis() % 10000;
    }
}
