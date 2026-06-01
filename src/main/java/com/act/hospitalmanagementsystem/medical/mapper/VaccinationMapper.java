package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.CreateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.VaccinationDTO;
import com.act.hospitalmanagementsystem.medical.entity.Vaccination;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {
    VaccinationDTO toDTO(Vaccination vaccination);
    List<VaccinationDTO> toDTOList(List<Vaccination> vaccinations);
    Vaccination toEntity(CreateVaccinationRequest request);
    void updateEntityFromDTO(UpdateVaccinationRequest request, @MappingTarget Vaccination vaccination);
}
