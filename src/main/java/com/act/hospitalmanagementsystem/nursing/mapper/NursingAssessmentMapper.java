package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingAssessmentDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NursingAssessmentMapper {
    NursingAssessmentDTO toDTO(NursingAssessment nursingAssessment);
    List<NursingAssessmentDTO> toDTOList(List<NursingAssessment> nursingAssessments);
    NursingAssessment toEntity(CreateNursingAssessmentRequest request);
    void updateEntityFromDTO(UpdateNursingAssessmentRequest request, @MappingTarget NursingAssessment nursingAssessment);
}
