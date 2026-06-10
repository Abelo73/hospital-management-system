package com.act.hospitalmanagementsystem.appointment.service;

import com.act.hospitalmanagementsystem.appointment.dto.AppointmentDTO;
import com.act.hospitalmanagementsystem.appointment.dto.CreateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.dto.UpdateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.entity.Appointment;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import com.act.hospitalmanagementsystem.appointment.repository.AppointmentRepository;
import com.act.hospitalmanagementsystem.appointment.mapper.AppointmentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment testAppointment;
    private AppointmentDTO appointmentDTO;
    private CreateAppointmentRequest createRequest;
    private UpdateAppointmentRequest updateRequest;
    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;

    @BeforeEach
    void setUp() {
        appointmentId = UUID.randomUUID();
        patientId = UUID.randomUUID();
        doctorId = UUID.randomUUID();

        testAppointment = new Appointment();
        testAppointment.setId(appointmentId);
        testAppointment.setPatientId(patientId);
        testAppointment.setDoctorId(doctorId);
        testAppointment.setAppointmentType(AppointmentType.CONSULTATION);
        testAppointment.setStatus(AppointmentStatus.SCHEDULED);
        testAppointment.setAppointmentDate(LocalDate.now().plusDays(1));
        testAppointment.setStartTime(LocalTime.of(9, 0));
        testAppointment.setEndTime(LocalTime.of(9, 30));
        testAppointment.setDurationMinutes(30);
        testAppointment.setReason("Regular checkup");
        testAppointment.setPriority("NORMAL");
        testAppointment.setIsVirtual(false);

        appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);
        appointmentDTO.setPatientId(patientId);
        appointmentDTO.setDoctorId(doctorId);
        appointmentDTO.setAppointmentType(AppointmentType.CONSULTATION);
        appointmentDTO.setStatus(AppointmentStatus.SCHEDULED);

        createRequest = new CreateAppointmentRequest();
        createRequest.setPatientId(patientId);
        createRequest.setDoctorId(doctorId);
        createRequest.setAppointmentType(AppointmentType.CONSULTATION);
        createRequest.setAppointmentDate(LocalDate.now().plusDays(1));
        createRequest.setStartTime(LocalTime.of(9, 0));
        createRequest.setEndTime(LocalTime.of(9, 30));
        createRequest.setReason("Regular checkup");

        updateRequest = new UpdateAppointmentRequest();
        updateRequest.setStatus(AppointmentStatus.CONFIRMED);
        updateRequest.setPriority("HIGH");
    }

    @Test
    void testGetAppointmentById_Success() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment));
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.getAppointmentById(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
        assertEquals(AppointmentType.CONSULTATION, result.getAppointmentType());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentMapper, times(1)).toDTO(testAppointment);
    }

    @Test
    void testGetAppointmentById_NotFound() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> appointmentService.getAppointmentById(appointmentId));
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentMapper, never()).toDTO(any());
    }

    @Test
    void testCreateAppointment_Success() {
        when(appointmentMapper.toEntity(createRequest)).thenReturn(testAppointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.createAppointment(createRequest);

        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testUpdateAppointment_Success() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.updateAppointment(appointmentId, updateRequest);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testUpdateAppointment_NotFound() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> appointmentService.updateAppointment(appointmentId, updateRequest));
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void testCancelAppointment_Success() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        appointmentService.cancelAppointment(appointmentId);

        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testGetAppointmentsByPatientId_Success() {
        when(appointmentRepository.findByPatientId(patientId))
                .thenReturn(java.util.List.of(testAppointment));
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        var results = appointmentService.getAppointmentsByPatientId(patientId);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(appointmentRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testGetAppointmentsByDoctorId_Success() {
        when(appointmentRepository.findByDoctorId(doctorId))
                .thenReturn(java.util.List.of(testAppointment));
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        var results = appointmentService.getAppointmentsByDoctorId(doctorId);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(appointmentRepository, times(1)).findByDoctorId(doctorId);
    }

    @Test
    void testGetAppointmentsByDate_Success() {
        LocalDate date = LocalDate.now().plusDays(1);
        when(appointmentRepository.findByAppointmentDate(date))
                .thenReturn(java.util.List.of(testAppointment));
        when(appointmentMapper.toDTO(testAppointment)).thenReturn(appointmentDTO);

        var results = appointmentService.getAppointmentsByDate(date);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(appointmentRepository, times(1)).findByAppointmentDate(date);
    }
}
