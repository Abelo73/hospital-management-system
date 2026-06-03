package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.dto.MedicationAdministrationDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.entity.MedicationAdministration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationAdministrationMapper {
    MedicationAdministrationDTO toDTO(MedicationAdministration medicationAdministration);
    List<MedicationAdministrationDTO> toDTOList(List<MedicationAdministration> medicationAdministrations);
    MedicationAdministration toEntity(CreateMedicationAdministrationRequest request);
    void updateEntityFromDTO(UpdateMedicationAdministrationRequest request, @MappingTarget MedicationAdministration medicationAdministration);
}
