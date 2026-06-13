package com.act.hospitalmanagementsystem.pharmacy.mapper;

import com.act.hospitalmanagementsystem.pharmacy.dto.DispensingDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Dispensing;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DispensingMapper {

    public DispensingDTO toDTO(Dispensing dispensing) {
        if (dispensing == null) {
            return null;
        }

        DispensingDTO dto = new DispensingDTO();
        dto.setId(dispensing.getId());
        dto.setDispensingNumber(dispensing.getDispensingNumber());
        dto.setPrescriptionId(dispensing.getPrescription() != null ? dispensing.getPrescription().getId() : null);
        dto.setPatientId(dispensing.getPatientId());
        dto.setDispensingDate(dispensing.getDispensingDate());
        dto.setStatus(dispensing.getStatus());
        dto.setDispensedBy(dispensing.getDispensedBy());
        dto.setVerifiedBy(dispensing.getVerifiedBy());
        dto.setTotalAmount(dispensing.getTotalAmount());
        dto.setDiscountAmount(dispensing.getDiscountAmount());
        dto.setNetAmount(dispensing.getNetAmount());
        dto.setPaymentMethod(dispensing.getPaymentMethod());
        dto.setInsuranceClaimId(dispensing.getInsuranceClaimId());
        dto.setCounselingProvided(dispensing.getCounselingProvided());
        dto.setCounselingNotes(dispensing.getCounselingNotes());
        dto.setCreatedAt(dispensing.getCreatedAt());
        dto.setCreatedBy(dispensing.getCreatedBy());
        return dto;
    }

    public List<DispensingDTO> toDTOList(List<Dispensing> dispensings) {
        return dispensings.stream().map(this::toDTO).toList();
    }
}
