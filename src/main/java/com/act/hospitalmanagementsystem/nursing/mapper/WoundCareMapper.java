package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.WoundCareDTO;
import com.act.hospitalmanagementsystem.nursing.entity.WoundCare;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WoundCareMapper {
    WoundCareDTO toDTO(WoundCare woundCare);
    List<WoundCareDTO> toDTOList(List<WoundCare> woundCareList);
    WoundCare toEntity(CreateWoundCareRequest request);
    void updateEntityFromDTO(UpdateWoundCareRequest request, @MappingTarget WoundCare woundCare);
}
