package com.act.hospitalmanagementsystem.patient.mapper;

import com.act.hospitalmanagementsystem.patient.dto.PatientDTO;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);

    List<PatientDTO> toDTOList(List<Patient> patients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalRecordNumber", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalRecordNumber", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(PatientDTO patientDTO, @MappingTarget Patient patient);
}
