package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.CreateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicationDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.entity.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationDTO toDTO(Medication medication);
    List<MedicationDTO> toDTOList(List<Medication> medications);
    Medication toEntity(CreateMedicationRequest request);
    void updateEntityFromDTO(UpdateMedicationRequest request, @MappingTarget Medication medication);
}
