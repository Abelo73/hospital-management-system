package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.dto.IncidentReportDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.entity.IncidentReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidentReportMapper {
    IncidentReportDTO toDTO(IncidentReport incidentReport);
    List<IncidentReportDTO> toDTOList(List<IncidentReport> incidentReports);
    IncidentReport toEntity(CreateIncidentReportRequest request);
    void updateEntityFromDTO(UpdateIncidentReportRequest request, @MappingTarget IncidentReport incidentReport);
}
