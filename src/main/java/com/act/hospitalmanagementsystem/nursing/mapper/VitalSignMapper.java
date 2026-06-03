package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.VitalSignDTO;
import com.act.hospitalmanagementsystem.nursing.entity.VitalSign;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VitalSignMapper {
    VitalSignDTO toDTO(VitalSign vitalSign);
    List<VitalSignDTO> toDTOList(List<VitalSign> vitalSigns);
    VitalSign toEntity(CreateVitalSignRequest request);
    void updateEntityFromDTO(UpdateVitalSignRequest request, @MappingTarget VitalSign vitalSign);
}
