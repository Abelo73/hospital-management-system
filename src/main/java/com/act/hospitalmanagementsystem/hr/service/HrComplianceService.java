package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.ComplianceDTO;
import com.act.hospitalmanagementsystem.hr.entity.Compliance;
import com.act.hospitalmanagementsystem.hr.repository.ComplianceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service("hrComplianceService")
@RequiredArgsConstructor
public class HrComplianceService {

    private final ComplianceRepository complianceRepository;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<ComplianceDTO>> getCompliance(UUID employeeId, String complianceType, String status, Pageable pageable) {
        try {
            Page<Compliance> compliance;
            if (employeeId != null) {
                compliance = complianceRepository.findByEmployeeId(employeeId, pageable);
            } else if (complianceType != null) {
                compliance = complianceRepository.findByComplianceType(complianceType, pageable);
            } else if (status != null) {
                compliance = complianceRepository.findByStatus(status, pageable);
            } else {
                compliance = complianceRepository.findAll(pageable);
            }
            List<ComplianceDTO> dtoList = compliance.getContent().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return BaseResponseDTO.success("Compliance records retrieved", dtoList);
        } catch (Exception e) {
            log.error("Error getting compliance records", e);
            return BaseResponseDTO.error("Failed to get compliance records: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<ComplianceDTO> getComplianceById(UUID id) {
        try {
            Compliance compliance = complianceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Compliance record not found"));
            return BaseResponseDTO.success("Compliance record retrieved", toDTO(compliance));
        } catch (Exception e) {
            log.error("Error getting compliance record", e);
            return BaseResponseDTO.error("Failed to get compliance record: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<ComplianceDTO> createCompliance(Compliance compliance, String createdBy) {
        try {
            compliance.setCreatedBy(createdBy);
            Compliance saved = complianceRepository.save(compliance);
            return BaseResponseDTO.success("Compliance record created successfully", toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating compliance record", e);
            return BaseResponseDTO.error("Failed to create compliance record: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<ComplianceDTO> updateCompliance(UUID id, Compliance compliance, String updatedBy) {
        try {
            Compliance existing = complianceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Compliance record not found"));

            existing.setEmployeeId(compliance.getEmployeeId());
            existing.setComplianceType(compliance.getComplianceType());
            existing.setDocumentName(compliance.getDocumentName());
            existing.setDocumentUrl(compliance.getDocumentUrl());
            existing.setIssuingAuthority(compliance.getIssuingAuthority());
            existing.setIssueDate(compliance.getIssueDate());
            existing.setExpiryDate(compliance.getExpiryDate());
            existing.setStatus(compliance.getStatus());
            existing.setReminderDate(compliance.getReminderDate());
            existing.setNotes(compliance.getNotes());
            existing.setUpdatedBy(updatedBy);

            Compliance saved = complianceRepository.save(existing);
            return BaseResponseDTO.success("Compliance record updated successfully", toDTO(saved));
        } catch (Exception e) {
            log.error("Error updating compliance record", e);
            return BaseResponseDTO.error("Failed to update compliance record: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteCompliance(UUID id) {
        try {
            complianceRepository.deleteById(id);
            return BaseResponseDTO.<Void>success("Compliance record deleted successfully", null);
        } catch (Exception e) {
            log.error("Error deleting compliance record", e);
            return BaseResponseDTO.error("Failed to delete compliance record: " + e.getMessage());
        }
    }

    private ComplianceDTO toDTO(Compliance compliance) {
        ComplianceDTO dto = new ComplianceDTO();
        dto.setId(compliance.getId());
        dto.setEmployeeId(compliance.getEmployeeId());
        dto.setComplianceType(compliance.getComplianceType());
        dto.setDocumentName(compliance.getDocumentName());
        dto.setDocumentUrl(compliance.getDocumentUrl());
        dto.setIssuingAuthority(compliance.getIssuingAuthority());
        dto.setIssueDate(compliance.getIssueDate());
        dto.setExpiryDate(compliance.getExpiryDate());
        dto.setStatus(compliance.getStatus());
        dto.setReminderDate(compliance.getReminderDate());
        dto.setNotes(compliance.getNotes());
        dto.setCreatedAt(compliance.getCreatedAt());
        dto.setCreatedBy(compliance.getCreatedBy());
        return dto;
    }
}
