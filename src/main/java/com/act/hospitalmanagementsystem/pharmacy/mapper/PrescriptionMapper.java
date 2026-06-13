package com.act.hospitalmanagementsystem.pharmacy.mapper;

import com.act.hospitalmanagementsystem.pharmacy.dto.PrescriptionDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Prescription;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrescriptionMapper {

    public PrescriptionDTO toDTO(Prescription prescription) {
        if (prescription == null) {
            return null;
        }

        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(prescription.getId());
        dto.setPrescriptionNumber(prescription.getPrescriptionNumber());
        dto.setPatientId(prescription.getPatientId());
        dto.setDoctorId(prescription.getDoctorId());
        dto.setPrescriptionDate(prescription.getPrescriptionDate());
        dto.setStatus(prescription.getStatus());
        dto.setPriority(prescription.getPriority());
        dto.setFacility(prescription.getFacility());
        dto.setDepartment(prescription.getDepartment());
        dto.setNotes(prescription.getNotes());
        dto.setValidatedBy(prescription.getValidatedBy());
        dto.setValidatedAt(prescription.getValidatedAt());
        dto.setDispensedBy(prescription.getDispensedBy());
        dto.setDispensedAt(prescription.getDispensedAt());
        dto.setCreatedAt(prescription.getCreatedAt());
        dto.setCreatedBy(prescription.getCreatedBy());
        return dto;
    }

    public List<PrescriptionDTO> toDTOList(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::toDTO).toList();
    }
}
