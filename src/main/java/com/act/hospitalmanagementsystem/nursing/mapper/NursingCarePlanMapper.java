package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingCarePlanDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingCarePlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NursingCarePlanMapper {
    NursingCarePlanDTO toDTO(NursingCarePlan nursingCarePlan);
    List<NursingCarePlanDTO> toDTOList(List<NursingCarePlan> nursingCarePlans);
    NursingCarePlan toEntity(CreateNursingCarePlanRequest request);
    void updateEntityFromDTO(UpdateNursingCarePlanRequest request, @MappingTarget NursingCarePlan nursingCarePlan);
}
