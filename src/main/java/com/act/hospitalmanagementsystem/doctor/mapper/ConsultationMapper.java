package com.act.hospitalmanagementsystem.doctor.mapper;

import com.act.hospitalmanagementsystem.doctor.dto.ConsultationDTO;
import com.act.hospitalmanagementsystem.doctor.dto.DiagnosisDTO;
import com.act.hospitalmanagementsystem.doctor.dto.PrescriptionDTO;
import com.act.hospitalmanagementsystem.doctor.entity.Consultation;
import com.act.hospitalmanagementsystem.doctor.entity.Diagnosis;
import com.act.hospitalmanagementsystem.doctor.entity.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", expression = "java(consultation.getPatient().getFirstName() + \" \" + consultation.getPatient().getLastName())")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorName", expression = "java(consultation.getDoctor().getFirstName() + \" \" + consultation.getDoctor().getLastName())")
    ConsultationDTO toDTO(Consultation consultation);

    List<ConsultationDTO> toDTOList(List<Consultation> consultations);

    @Mapping(target = "consultationId", source = "consultation.id")
    DiagnosisDTO toDTO(Diagnosis diagnosis);

    @Mapping(target = "consultationId", source = "consultation.id")
    PrescriptionDTO toDTO(Prescription prescription);
}
