package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.BenefitsDTO;
import com.act.hospitalmanagementsystem.hr.entity.Benefits;
import com.act.hospitalmanagementsystem.hr.repository.BenefitsRepository;
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
@Service
@RequiredArgsConstructor
public class BenefitsService {

    private final BenefitsRepository benefitsRepository;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<BenefitsDTO>> getBenefits(UUID employeeId, String benefitType, String status, Pageable pageable) {
        try {
            Page<Benefits> benefits;
            if (employeeId != null) {
                benefits = benefitsRepository.findByEmployeeId(employeeId, pageable);
            } else if (benefitType != null) {
                benefits = benefitsRepository.findByBenefitType(benefitType, pageable);
            } else if (status != null) {
                benefits = benefitsRepository.findByStatus(status, pageable);
            } else {
                benefits = benefitsRepository.findAll(pageable);
            }
            List<BenefitsDTO> dtoList = benefits.getContent().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return BaseResponseDTO.success("Benefits retrieved", dtoList);
        } catch (Exception e) {
            log.error("Error getting benefits", e);
            return BaseResponseDTO.error("Failed to get benefits: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<BenefitsDTO> getBenefitById(UUID id) {
        try {
            Benefits benefit = benefitsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Benefit not found"));
            return BaseResponseDTO.success("Benefit retrieved", toDTO(benefit));
        } catch (Exception e) {
            log.error("Error getting benefit", e);
            return BaseResponseDTO.error("Failed to get benefit: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<BenefitsDTO> createBenefit(Benefits benefit, String createdBy) {
        try {
            benefit.setCreatedBy(createdBy);
            Benefits saved = benefitsRepository.save(benefit);
            return BaseResponseDTO.success("Benefit created successfully", toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating benefit", e);
            return BaseResponseDTO.error("Failed to create benefit: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<BenefitsDTO> updateBenefit(UUID id, Benefits benefit, String updatedBy) {
        try {
            Benefits existing = benefitsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Benefit not found"));

            existing.setEmployeeId(benefit.getEmployeeId());
            existing.setBenefitType(benefit.getBenefitType());
            existing.setPlanName(benefit.getPlanName());
            existing.setProvider(benefit.getProvider());
            existing.setCoverageAmount(benefit.getCoverageAmount());
            existing.setEmployeeContribution(benefit.getEmployeeContribution());
            existing.setEmployerContribution(benefit.getEmployerContribution());
            existing.setEnrollmentDate(benefit.getEnrollmentDate());
            existing.setEffectiveDate(benefit.getEffectiveDate());
            existing.setTerminationDate(benefit.getTerminationDate());
            existing.setStatus(benefit.getStatus());
            existing.setDependents(benefit.getDependents());
            existing.setNotes(benefit.getNotes());
            existing.setUpdatedBy(updatedBy);

            Benefits saved = benefitsRepository.save(existing);
            return BaseResponseDTO.success("Benefit updated successfully", toDTO(saved));
        } catch (Exception e) {
            log.error("Error updating benefit", e);
            return BaseResponseDTO.error("Failed to update benefit: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteBenefit(UUID id) {
        try {
            benefitsRepository.deleteById(id);
            return BaseResponseDTO.<Void>success("Benefit deleted successfully", null);
        } catch (Exception e) {
            log.error("Error deleting benefit", e);
            return BaseResponseDTO.error("Failed to delete benefit: " + e.getMessage());
        }
    }

    private BenefitsDTO toDTO(Benefits benefit) {
        BenefitsDTO dto = new BenefitsDTO();
        dto.setId(benefit.getId());
        dto.setEmployeeId(benefit.getEmployeeId());
        dto.setBenefitType(benefit.getBenefitType());
        dto.setPlanName(benefit.getPlanName());
        dto.setProvider(benefit.getProvider());
        dto.setCoverageAmount(benefit.getCoverageAmount());
        dto.setEmployeeContribution(benefit.getEmployeeContribution());
        dto.setEmployerContribution(benefit.getEmployerContribution());
        dto.setEnrollmentDate(benefit.getEnrollmentDate());
        dto.setEffectiveDate(benefit.getEffectiveDate());
        dto.setTerminationDate(benefit.getTerminationDate());
        dto.setStatus(benefit.getStatus());
        dto.setDependents(benefit.getDependents());
        dto.setNotes(benefit.getNotes());
        dto.setCreatedAt(benefit.getCreatedAt());
        dto.setCreatedBy(benefit.getCreatedBy());
        return dto;
    }
}
