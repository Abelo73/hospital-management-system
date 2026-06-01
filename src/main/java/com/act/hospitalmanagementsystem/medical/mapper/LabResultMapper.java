package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.CreateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.dto.LabResultDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.entity.LabResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabResultMapper {
    LabResultDTO toDTO(LabResult labResult);
    List<LabResultDTO> toDTOList(List<LabResult> labResults);
    LabResult toEntity(CreateLabResultRequest request);
    void updateEntityFromDTO(UpdateLabResultRequest request, @MappingTarget LabResult labResult);
}
