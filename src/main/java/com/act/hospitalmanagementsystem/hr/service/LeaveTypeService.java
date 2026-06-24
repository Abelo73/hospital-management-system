package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.LeaveTypeDTO;
import com.act.hospitalmanagementsystem.hr.entity.LeaveType;
import com.act.hospitalmanagementsystem.hr.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<LeaveTypeDTO>> getAllLeaveTypes(boolean activeOnly) {
        try {
            List<LeaveType> types = activeOnly
                    ? leaveTypeRepository.findByIsActiveTrue()
                    : leaveTypeRepository.findAll();
            return BaseResponseDTO.success("Leave types retrieved", types.stream().map(this::toDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error getting leave types", e);
            return BaseResponseDTO.error("Failed to get leave types: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<LeaveTypeDTO> getById(UUID id) {
        try {
            LeaveType lt = leaveTypeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Leave type not found"));
            return BaseResponseDTO.success("Leave type retrieved", toDTO(lt));
        } catch (Exception e) {
            log.error("Error getting leave type", e);
            return BaseResponseDTO.error("Failed to get leave type: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<LeaveTypeDTO> createLeaveType(LeaveTypeDTO dto, String createdBy) {
        try {
            if (leaveTypeRepository.existsByCode(dto.getCode())) {
                return BaseResponseDTO.error("Leave type code already exists: " + dto.getCode());
            }
            LeaveType lt = fromDTO(dto);
            lt.setCreatedBy(createdBy);
            LeaveType saved = leaveTypeRepository.save(lt);
            return BaseResponseDTO.success("Leave type created", toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating leave type", e);
            return BaseResponseDTO.error("Failed to create leave type: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<LeaveTypeDTO> updateLeaveType(UUID id, LeaveTypeDTO dto, String updatedBy) {
        try {
            LeaveType lt = leaveTypeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Leave type not found"));
            lt.setName(dto.getName());
            lt.setAnnualDays(dto.getAnnualDays());
            lt.setIsPaid(dto.getIsPaid());
            lt.setRequiresApproval(dto.getRequiresApproval());
            lt.setRequiresAttachment(dto.getRequiresAttachment());
            lt.setIsGenderSpecific(dto.getIsGenderSpecific());
            lt.setApplicableGender(dto.getApplicableGender());
            lt.setMaxCarryoverDays(dto.getMaxCarryoverDays());
            lt.setMinServiceMonthsRequired(dto.getMinServiceMonthsRequired());
            lt.setAccrualFrequency(dto.getAccrualFrequency());
            lt.setIsActive(dto.getIsActive());
            lt.setUpdatedBy(updatedBy);
            LeaveType saved = leaveTypeRepository.save(lt);
            return BaseResponseDTO.success("Leave type updated", toDTO(saved));
        } catch (Exception e) {
            log.error("Error updating leave type", e);
            return BaseResponseDTO.error("Failed to update leave type: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteLeaveType(UUID id) {
        try {
            leaveTypeRepository.deleteById(id);
            return BaseResponseDTO.<Void>success("Leave type deleted", null);
        } catch (Exception e) {
            log.error("Error deleting leave type", e);
            return BaseResponseDTO.error("Failed to delete leave type: " + e.getMessage());
        }
    }

    private LeaveTypeDTO toDTO(LeaveType lt) {
        LeaveTypeDTO dto = new LeaveTypeDTO();
        dto.setId(lt.getId());
        dto.setName(lt.getName());
        dto.setCode(lt.getCode());
        dto.setAnnualDays(lt.getAnnualDays());
        dto.setIsPaid(lt.getIsPaid());
        dto.setRequiresApproval(lt.getRequiresApproval());
        dto.setRequiresAttachment(lt.getRequiresAttachment());
        dto.setIsGenderSpecific(lt.getIsGenderSpecific());
        dto.setApplicableGender(lt.getApplicableGender());
        dto.setMaxCarryoverDays(lt.getMaxCarryoverDays());
        dto.setMinServiceMonthsRequired(lt.getMinServiceMonthsRequired());
        dto.setAccrualFrequency(lt.getAccrualFrequency());
        dto.setIsActive(lt.getIsActive());
        dto.setCreatedAt(lt.getCreatedAt());
        dto.setCreatedBy(lt.getCreatedBy());
        return dto;
    }

    private LeaveType fromDTO(LeaveTypeDTO dto) {
        LeaveType lt = new LeaveType();
        lt.setName(dto.getName());
        lt.setCode(dto.getCode());
        lt.setAnnualDays(dto.getAnnualDays() != null ? dto.getAnnualDays() : 0);
        lt.setIsPaid(dto.getIsPaid() != null ? dto.getIsPaid() : true);
        lt.setRequiresApproval(dto.getRequiresApproval() != null ? dto.getRequiresApproval() : true);
        lt.setRequiresAttachment(dto.getRequiresAttachment() != null ? dto.getRequiresAttachment() : false);
        lt.setIsGenderSpecific(dto.getIsGenderSpecific() != null ? dto.getIsGenderSpecific() : false);
        lt.setApplicableGender(dto.getApplicableGender());
        lt.setMaxCarryoverDays(dto.getMaxCarryoverDays() != null ? dto.getMaxCarryoverDays() : 0);
        lt.setMinServiceMonthsRequired(dto.getMinServiceMonthsRequired() != null ? dto.getMinServiceMonthsRequired() : 0);
        lt.setAccrualFrequency(dto.getAccrualFrequency() != null ? dto.getAccrualFrequency() : "ANNUAL");
        lt.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return lt;
    }
}
