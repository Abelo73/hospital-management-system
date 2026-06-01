package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.CreateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicalRecordDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecordDTO toDTO(MedicalRecord medicalRecord);
    List<MedicalRecordDTO> toDTOList(List<MedicalRecord> medicalRecords);
    MedicalRecord toEntity(CreateMedicalRecordRequest request);
    void updateEntityFromDTO(UpdateMedicalRecordRequest request, @MappingTarget MedicalRecord medicalRecord);
}
