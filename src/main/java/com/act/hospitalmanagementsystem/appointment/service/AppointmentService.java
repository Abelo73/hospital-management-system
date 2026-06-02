package com.act.hospitalmanagementsystem.appointment.service;

import com.act.hospitalmanagementsystem.appointment.dto.AppointmentDTO;
import com.act.hospitalmanagementsystem.appointment.dto.CreateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.dto.UpdateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.entity.Appointment;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.mapper.AppointmentMapper;
import com.act.hospitalmanagementsystem.appointment.repository.AppointmentRepository;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentDTO createAppointment(CreateAppointmentRequest request) {
        // Check for overlapping appointments
        List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getDoctorId()
        );

        if (!overlappingAppointments.isEmpty()) {
            throw new BusinessException("Doctor already has an appointment during this time slot");
        }

        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    public AppointmentDTO getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return appointmentMapper.toDTO(appointment);
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(UUID patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientIdAndDeletedFalseOrderByAppointmentDateDescStartTimeAsc(patientId);
        return appointmentMapper.toDTOList(appointments);
    }

    public Page<AppointmentDTO> getAppointmentsByPatientId(UUID patientId, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(UUID doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDeletedFalseOrderByAppointmentDateDescStartTimeAsc(doctorId);
        return appointmentMapper.toDTOList(appointments);
    }

    public Page<AppointmentDTO> getAppointmentsByDoctorId(UUID doctorId, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByDoctorIdAndDeletedFalse(doctorId, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByStatus(AppointmentStatus status, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByStatusAndDeletedFalse(status, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByType(com.act.hospitalmanagementsystem.appointment.enums.AppointmentType appointmentType, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByAppointmentTypeAndDeletedFalse(appointmentType, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByPatientIdAndStatus(UUID patientId, AppointmentStatus status, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByPatientIdAndStatusAndDeletedFalse(patientId, status, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByDoctorIdAndStatus(UUID doctorId, AppointmentStatus status, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByDoctorIdAndStatusAndDeletedFalse(doctorId, status, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByPatientIdAndDoctorId(UUID patientId, UUID doctorId, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByPatientIdAndDoctorIdAndDeletedFalse(patientId, doctorId, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> searchAppointments(String searchTerm, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.searchAppointments(searchTerm, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> searchPatientAppointments(UUID patientId, String searchTerm, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.searchPatientAppointments(patientId, searchTerm, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> searchDoctorAppointments(UUID doctorId, String searchTerm, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.searchDoctorAppointments(doctorId, searchTerm, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public Page<AppointmentDTO> getAppointmentsByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByAppointmentDateBetweenAndDeletedFalse(startDate, endDate, pageable);
        return appointments.map(appointmentMapper::toDTO);
    }

    public List<AppointmentDTO> getAppointmentsByDate(java.time.LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateAndDeletedFalse(appointmentDate);
        return appointmentMapper.toDTOList(appointments);
    }

    public List<AppointmentDTO> getDoctorAppointmentsForDate(java.time.LocalDate appointmentDate, UUID doctorId) {
        List<Appointment> appointments = appointmentRepository.findDoctorAppointmentsForDate(appointmentDate, doctorId);
        return appointmentMapper.toDTOList(appointments);
    }

    public Long countDoctorAppointmentsForDate(UUID doctorId, java.time.LocalDate appointmentDate) {
        return appointmentRepository.countDoctorAppointmentsForDate(doctorId, appointmentDate);
    }

    public AppointmentDTO updateAppointment(UUID id, UpdateAppointmentRequest request) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        // If changing date/time, check for overlaps
        if (request.getAppointmentDate() != null || request.getStartTime() != null || request.getEndTime() != null) {
            java.time.LocalDate date = request.getAppointmentDate() != null ? request.getAppointmentDate() : appointment.getAppointmentDate();
            java.time.LocalTime startTime = request.getStartTime() != null ? request.getStartTime() : appointment.getStartTime();
            java.time.LocalTime endTime = request.getEndTime() != null ? request.getEndTime() : appointment.getEndTime();
            UUID doctorId = request.getDoctorId() != null ? request.getDoctorId() : appointment.getDoctorId();

            List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                    date, startTime, endTime, doctorId
            );

            overlappingAppointments.removeIf(a -> a.getId().equals(id));

            if (!overlappingAppointments.isEmpty()) {
                throw new BusinessException("Doctor already has an appointment during this time slot");
            }
        }

        appointmentMapper.updateEntityFromDTO(request, appointment);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    public AppointmentDTO checkInAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED && appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new BusinessException("Appointment must be SCHEDULED or CONFIRMED to check in");
        }

        appointment.setStatus(AppointmentStatus.IN_PROGRESS);
        appointment.setCheckInTime(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    public AppointmentDTO checkOutAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointment.getStatus() != AppointmentStatus.IN_PROGRESS) {
            throw new BusinessException("Appointment must be IN_PROGRESS to check out");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setCheckOutTime(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    public AppointmentDTO cancelAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED || appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BusinessException("Cannot cancel a completed or already cancelled appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
    }
}
