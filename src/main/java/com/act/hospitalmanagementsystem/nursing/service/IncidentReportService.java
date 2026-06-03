package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.dto.IncidentReportDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.entity.IncidentReport;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
import com.act.hospitalmanagementsystem.nursing.mapper.IncidentReportMapper;
import com.act.hospitalmanagementsystem.nursing.repository.IncidentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IncidentReportService {

    private final IncidentReportRepository incidentReportRepository;
    private final IncidentReportMapper incidentReportMapper;

    public IncidentReportDTO createIncidentReport(CreateIncidentReportRequest request) {
        IncidentReport incidentReport = incidentReportMapper.toEntity(request);
        incidentReport = incidentReportRepository.save(incidentReport);
        return incidentReportMapper.toDTO(incidentReport);
    }

    public IncidentReportDTO getIncidentReportById(UUID id) {
        IncidentReport incidentReport = incidentReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident Report", "id", id));
        return incidentReportMapper.toDTO(incidentReport);
    }

    public Page<IncidentReportDTO> getIncidentReportsByPatientId(UUID patientId, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> getIncidentReportsByType(IncidentType incidentType, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findByIncidentTypeAndDeletedFalse(incidentType, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> getIncidentReportsBySeverity(IncidentSeverity severity, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findBySeverityAndDeletedFalse(severity, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> getIncidentReportsByStatus(IncidentStatus status, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findByStatusAndDeletedFalse(status, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> getIncidentReportsByReportedBy(UUID reportedBy, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findByReportedByAndDeletedFalse(reportedBy, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> getIncidentReportsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.findByDateRange(startDate, endDate, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public Page<IncidentReportDTO> searchIncidentReports(String searchTerm, Pageable pageable) {
        Page<IncidentReport> incidentReports = incidentReportRepository.searchIncidents(searchTerm, pageable);
        return incidentReports.map(incidentReportMapper::toDTO);
    }

    public IncidentReportDTO updateIncidentReport(UUID id, UpdateIncidentReportRequest request) {
        IncidentReport incidentReport = incidentReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident Report", "id", id));
        incidentReportMapper.updateEntityFromDTO(request, incidentReport);
        incidentReport = incidentReportRepository.save(incidentReport);
        return incidentReportMapper.toDTO(incidentReport);
    }

    public void deleteIncidentReport(UUID id) {
        IncidentReport incidentReport = incidentReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident Report", "id", id));
        incidentReport.setDeleted(true);
        incidentReportRepository.save(incidentReport);
    }
}
