package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.CreateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.VaccinationDTO;
import com.act.hospitalmanagementsystem.medical.entity.Vaccination;
import com.act.hospitalmanagementsystem.medical.mapper.VaccinationMapper;
import com.act.hospitalmanagementsystem.medical.repository.VaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final VaccinationMapper vaccinationMapper;

    public VaccinationDTO createVaccination(CreateVaccinationRequest request) {
        Vaccination vaccination = vaccinationMapper.toEntity(request);
        vaccination = vaccinationRepository.save(vaccination);
        return vaccinationMapper.toDTO(vaccination);
    }

    public VaccinationDTO getVaccinationById(UUID id) {
        Vaccination vaccination = vaccinationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination", "id", id));
        return vaccinationMapper.toDTO(vaccination);
    }

    public List<VaccinationDTO> getVaccinationsByPatientId(UUID patientId) {
        List<Vaccination> vaccinations = vaccinationRepository.findByPatientIdAndDeletedFalseOrderByAdministrationDateDesc(patientId);
        return vaccinationMapper.toDTOList(vaccinations);
    }

    public Page<VaccinationDTO> getVaccinationsByPatientId(UUID patientId, Pageable pageable) {
        Page<Vaccination> vaccinations = vaccinationRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return vaccinations.map(vaccinationMapper::toDTO);
    }

    public Page<VaccinationDTO> searchPatientVaccinations(UUID patientId, String searchTerm, Pageable pageable) {
        Page<Vaccination> vaccinations = vaccinationRepository.searchPatientVaccinations(patientId, searchTerm, pageable);
        return vaccinations.map(vaccinationMapper::toDTO);
    }

    public VaccinationDTO updateVaccination(UUID id, UpdateVaccinationRequest request) {
        Vaccination vaccination = vaccinationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination", "id", id));
        vaccinationMapper.updateEntityFromDTO(request, vaccination);
        vaccination = vaccinationRepository.save(vaccination);
        return vaccinationMapper.toDTO(vaccination);
    }

    public void deleteVaccination(UUID id) {
        Vaccination vaccination = vaccinationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination", "id", id));
        vaccination.setDeleted(true);
        vaccinationRepository.save(vaccination);
    }
}
