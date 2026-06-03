package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingCarePlanDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingCarePlan;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
import com.act.hospitalmanagementsystem.nursing.mapper.NursingCarePlanMapper;
import com.act.hospitalmanagementsystem.nursing.repository.NursingCarePlanRepository;
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
public class NursingCarePlanService {

    private final NursingCarePlanRepository nursingCarePlanRepository;
    private final NursingCarePlanMapper nursingCarePlanMapper;

    public NursingCarePlanDTO createCarePlan(CreateNursingCarePlanRequest request) {
        NursingCarePlan carePlan = nursingCarePlanMapper.toEntity(request);
        carePlan = nursingCarePlanRepository.save(carePlan);
        return nursingCarePlanMapper.toDTO(carePlan);
    }

    public NursingCarePlanDTO getCarePlanById(UUID id) {
        NursingCarePlan carePlan = nursingCarePlanRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Care Plan", "id", id));
        return nursingCarePlanMapper.toDTO(carePlan);
    }

    public List<NursingCarePlanDTO> getCarePlansByPatientId(UUID patientId) {
        List<NursingCarePlan> carePlans = nursingCarePlanRepository.findByPatientIdAndDeletedFalseOrderByStartDateDesc(patientId);
        return nursingCarePlanMapper.toDTOList(carePlans);
    }

    public Page<NursingCarePlanDTO> getCarePlansByPatientId(UUID patientId, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public Page<NursingCarePlanDTO> getCarePlansByType(CarePlanType planType, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.findByPlanTypeAndDeletedFalse(planType, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public Page<NursingCarePlanDTO> getCarePlansByPatientIdAndType(UUID patientId, CarePlanType planType, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.findByPatientIdAndPlanTypeAndDeletedFalse(patientId, planType, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public Page<NursingCarePlanDTO> getCarePlansByStatus(CarePlanStatus status, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.findByStatusAndDeletedFalse(status, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public Page<NursingCarePlanDTO> getCarePlansByPrimaryNurse(UUID primaryNurseId, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.findByPrimaryNurseIdAndDeletedFalse(primaryNurseId, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public Page<NursingCarePlanDTO> searchCarePlans(String searchTerm, Pageable pageable) {
        Page<NursingCarePlan> carePlans = nursingCarePlanRepository.searchCarePlans(searchTerm, pageable);
        return carePlans.map(nursingCarePlanMapper::toDTO);
    }

    public NursingCarePlanDTO updateCarePlan(UUID id, UpdateNursingCarePlanRequest request) {
        NursingCarePlan carePlan = nursingCarePlanRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Care Plan", "id", id));
        nursingCarePlanMapper.updateEntityFromDTO(request, carePlan);
        carePlan = nursingCarePlanRepository.save(carePlan);
        return nursingCarePlanMapper.toDTO(carePlan);
    }

    public void deleteCarePlan(UUID id) {
        NursingCarePlan carePlan = nursingCarePlanRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Care Plan", "id", id));
        carePlan.setDeleted(true);
        nursingCarePlanRepository.save(carePlan);
    }
}
