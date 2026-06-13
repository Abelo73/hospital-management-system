package com.act.hospitalmanagementsystem.pharmacy.mapper;

import com.act.hospitalmanagementsystem.pharmacy.dto.DrugDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Drug;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DrugMapper {

    public DrugDTO toDTO(Drug drug) {
        if (drug == null) {
            return null;
        }

        DrugDTO dto = new DrugDTO();
        dto.setId(drug.getId());
        dto.setDrugCode(drug.getDrugCode());
        dto.setGenericName(drug.getGenericName());
        dto.setBrandName(drug.getBrandName());
        dto.setDrugCategory(drug.getDrugCategory());
        dto.setDosageForm(drug.getDosageForm());
        dto.setStrength(drug.getStrength());
        dto.setManufacturer(drug.getManufacturer());
        dto.setNdc(drug.getNdc());
        dto.setSchedule(drug.getSchedule());
        dto.setIsControlledSubstance(drug.getIsControlledSubstance());
        dto.setRequiresPrescription(drug.getRequiresPrescription());
        dto.setStorageConditions(drug.getStorageConditions());
        dto.setShelfLife(drug.getShelfLife());
        dto.setDescription(drug.getDescription());
        dto.setIndications(drug.getIndications());
        dto.setContraindications(drug.getContraindications());
        dto.setSideEffects(drug.getSideEffects());
        dto.setInteractions(drug.getInteractions());
        dto.setIsActive(drug.getIsActive());
        dto.setCreatedAt(drug.getCreatedAt());
        dto.setCreatedBy(drug.getCreatedBy());
        return dto;
    }

    public List<DrugDTO> toDTOList(List<Drug> drugs) {
        return drugs.stream().map(this::toDTO).toList();
    }
}
