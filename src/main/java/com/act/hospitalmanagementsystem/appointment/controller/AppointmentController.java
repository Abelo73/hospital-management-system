package com.act.hospitalmanagementsystem.appointment.controller;

import com.act.hospitalmanagementsystem.appointment.dto.AppointmentDTO;
import com.act.hospitalmanagementsystem.appointment.dto.CreateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.dto.UpdateAppointmentRequest;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import com.act.hospitalmanagementsystem.appointment.service.AppointmentService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('APPOINTMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        AppointmentDTO appointment = appointmentService.createAppointment(request);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> getAppointmentById(@PathVariable UUID id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<AppointmentDTO>>> getAppointmentsByPatientId(@PathVariable UUID patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<AppointmentDTO>>> getAppointmentsByDoctorId(@PathVariable UUID doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}/paginated")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByDoctorIdPaginated(
            @PathVariable UUID doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByStatus(
            @PathVariable AppointmentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/type/{appointmentType}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByType(
            @PathVariable AppointmentType appointmentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByType(appointmentType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByPatientIdAndStatus(
            @PathVariable UUID patientId,
            @PathVariable AppointmentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientIdAndStatus(patientId, status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}/status/{status}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByDoctorIdAndStatus(
            @PathVariable UUID doctorId,
            @PathVariable AppointmentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorIdAndStatus(doctorId, status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/patient/{patientId}/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByPatientIdAndDoctorId(
            @PathVariable UUID patientId,
            @PathVariable UUID doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientIdAndDoctorId(patientId, doctorId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> searchAppointments(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.searchAppointments(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> searchPatientAppointments(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.searchPatientAppointments(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}/search")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> searchDoctorAppointments(
            @PathVariable UUID doctorId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.searchDoctorAppointments(doctorId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AppointmentDTO>>> getAppointmentsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/date/{appointmentDate}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<AppointmentDTO>>> getAppointmentsByDate(@PathVariable LocalDate appointmentDate) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDate(appointmentDate);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}/date/{appointmentDate}")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<AppointmentDTO>>> getDoctorAppointmentsForDate(
            @PathVariable UUID doctorId,
            @PathVariable LocalDate appointmentDate
    ) {
        List<AppointmentDTO> appointments = appointmentService.getDoctorAppointmentsForDate(appointmentDate, doctorId);
        return ResponseEntity.ok(BaseResponseDTO.success(appointments));
    }

    @GetMapping("/doctor/{doctorId}/date/{appointmentDate}/count")
    @PreAuthorize("hasAuthority('APPOINTMENT_READ')")
    public ResponseEntity<BaseResponseDTO<Long>> countDoctorAppointmentsForDate(
            @PathVariable UUID doctorId,
            @PathVariable LocalDate appointmentDate
    ) {
        Long count = appointmentService.countDoctorAppointmentsForDate(doctorId, appointmentDate);
        return ResponseEntity.ok(BaseResponseDTO.success(count));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> updateAppointment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAppointmentRequest request
    ) {
        AppointmentDTO appointment = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @PostMapping("/{id}/check-in")
    @PreAuthorize("hasAuthority('APPOINTMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> checkInAppointment(@PathVariable UUID id) {
        AppointmentDTO appointment = appointmentService.checkInAppointment(id);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @PostMapping("/{id}/check-out")
    @PreAuthorize("hasAuthority('APPOINTMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> checkOutAppointment(@PathVariable UUID id) {
        AppointmentDTO appointment = appointmentService.checkOutAppointment(id);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('APPOINTMENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<AppointmentDTO>> cancelAppointment(@PathVariable UUID id) {
        AppointmentDTO appointment = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(BaseResponseDTO.success(appointment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('APPOINTMENT_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
