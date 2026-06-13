package com.act.hospitalmanagementsystem.pharmacy.mapper;

import com.act.hospitalmanagementsystem.pharmacy.dto.PatientMedicationDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.PatientMedication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientMedicationMapper {

    public PatientMedicationDTO toDTO(PatientMedication medication) {
        if (medication == null) {
            return null;
        }

        PatientMedicationDTO dto = new PatientMedicationDTO();
        dto.setId(medication.getId());
        dto.setPatientId(medication.getPatientId());
        dto.setDrugId(medication.getDrug() != null ? medication.getDrug().getId() : null);
        dto.setPrescriptionId(medication.getPrescription() != null ? medication.getPrescription().getId() : null);
        dto.setStartDate(medication.getStartDate());
        dto.setEndDate(medication.getEndDate());
        dto.setDosage(medication.getDosage());
        dto.setFrequency(medication.getFrequency());
        dto.setRoute(medication.getRoute());
        dto.setInstructions(medication.getInstructions());
        dto.setIsActive(medication.getIsActive());
        dto.setAdherence(medication.getAdherence());
        dto.setNotes(medication.getNotes());
        dto.setCreatedAt(medication.getCreatedAt());
        dto.setCreatedBy(medication.getCreatedBy());
        return dto;
    }

    public List<PatientMedicationDTO> toDTOList(List<PatientMedication> medications) {
        return medications.stream().map(this::toDTO).toList();
    }
}
