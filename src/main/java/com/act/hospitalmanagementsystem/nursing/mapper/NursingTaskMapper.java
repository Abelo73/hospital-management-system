package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingTaskDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingTask;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NursingTaskMapper {
    NursingTaskDTO toDTO(NursingTask nursingTask);
    List<NursingTaskDTO> toDTOList(List<NursingTask> nursingTasks);
    NursingTask toEntity(CreateNursingTaskRequest request);
    void updateEntityFromDTO(UpdateNursingTaskRequest request, @MappingTarget NursingTask nursingTask);
}
