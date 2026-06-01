package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.DiagnosisDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.entity.Diagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper {
    DiagnosisDTO toDTO(Diagnosis diagnosis);
    List<DiagnosisDTO> toDTOList(List<Diagnosis> diagnoses);
    Diagnosis toEntity(CreateDiagnosisRequest request);
    void updateEntityFromDTO(UpdateDiagnosisRequest request, @MappingTarget Diagnosis diagnosis);
}
