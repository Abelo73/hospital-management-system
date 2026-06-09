package com.act.hospitalmanagementsystem.laboratory.mapper;

import com.act.hospitalmanagementsystem.laboratory.dto.LabTestDTO;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestRequestDTO;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestRequestItemDTO;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTest;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequest;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequestItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LaboratoryMapper {

    LabTestDTO toDTO(LabTest test);
    List<LabTestDTO> toDTOList(List<LabTest> tests);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", expression = "java(request.getPatient().getFirstName() + \" \" + request.getPatient().getLastName())")
    @Mapping(target = "orderingProviderId", source = "orderingProvider.id")
    @Mapping(target = "orderingProviderName", expression = "java(request.getOrderingProvider().getFirstName() + \" \" + request.getOrderingProvider().getLastName())")
    LabTestRequestDTO toDTO(LabTestRequest request);

    List<LabTestRequestDTO> toRequestDTOList(List<LabTestRequest> requests);

    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "testName", source = "test.testName")
    LabTestRequestItemDTO toDTO(LabTestRequestItem item);
}
