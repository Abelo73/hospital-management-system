package com.act.hospitalmanagementsystem.appointment.mapper;

import com.act.hospitalmanagementsystem.appointment.dto.AppointmentDTO;
import com.act.hospitalmanagementsystem.appointment.dto.CreateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.dto.UpdateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDTO(Appointment appointment);
    List<AppointmentDTO> toDTOList(List<Appointment> appointments);
    Appointment toEntity(CreateAppointmentRequest request);
    void updateEntityFromDTO(UpdateAppointmentRequest request, @MappingTarget Appointment appointment);
}
