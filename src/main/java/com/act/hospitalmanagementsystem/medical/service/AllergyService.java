package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.AllergyDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.entity.Allergy;
import com.act.hospitalmanagementsystem.medical.mapper.AllergyMapper;
import com.act.hospitalmanagementsystem.medical.repository.AllergyRepository;
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
public class AllergyService {

    private final AllergyRepository allergyRepository;
    private final AllergyMapper allergyMapper;

    public AllergyDTO createAllergy(CreateAllergyRequest request) {
        Allergy allergy = allergyMapper.toEntity(request);
        allergy = allergyRepository.save(allergy);
        return allergyMapper.toDTO(allergy);
    }

    public AllergyDTO getAllergyById(UUID id) {
        Allergy allergy = allergyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));
        return allergyMapper.toDTO(allergy);
    }

    public List<AllergyDTO> getAllergiesByPatientId(UUID patientId) {
        List<Allergy> allergies = allergyRepository.findByPatientIdAndDeletedFalseOrderByOnsetDateDesc(patientId);
        return allergyMapper.toDTOList(allergies);
    }

    public Page<AllergyDTO> getAllergiesByPatientId(UUID patientId, Pageable pageable) {
        Page<Allergy> allergies = allergyRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return allergies.map(allergyMapper::toDTO);
    }

    public Page<AllergyDTO> searchPatientAllergies(UUID patientId, String searchTerm, Pageable pageable) {
        Page<Allergy> allergies = allergyRepository.searchPatientAllergies(patientId, searchTerm, pageable);
        return allergies.map(allergyMapper::toDTO);
    }

    public AllergyDTO updateAllergy(UUID id, UpdateAllergyRequest request) {
        Allergy allergy = allergyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));
        allergyMapper.updateEntityFromDTO(request, allergy);
        allergy = allergyRepository.save(allergy);
        return allergyMapper.toDTO(allergy);
    }

    public void deleteAllergy(UUID id) {
        Allergy allergy = allergyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));
        allergy.setDeleted(true);
        allergyRepository.save(allergy);
    }
}
