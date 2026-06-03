package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.dto.FluidBalanceDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.entity.FluidBalance;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
import com.act.hospitalmanagementsystem.nursing.mapper.FluidBalanceMapper;
import com.act.hospitalmanagementsystem.nursing.repository.FluidBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FluidBalanceService {

    private final FluidBalanceRepository fluidBalanceRepository;
    private final FluidBalanceMapper fluidBalanceMapper;

    public FluidBalanceDTO createFluidBalance(CreateFluidBalanceRequest request) {
        FluidBalance fluidBalance = fluidBalanceMapper.toEntity(request);
        fluidBalance = fluidBalanceRepository.save(fluidBalance);
        return fluidBalanceMapper.toDTO(fluidBalance);
    }

    public FluidBalanceDTO getFluidBalanceById(UUID id) {
        FluidBalance fluidBalance = fluidBalanceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fluid Balance", "id", id));
        return fluidBalanceMapper.toDTO(fluidBalance);
    }

    public List<FluidBalanceDTO> getFluidBalanceByPatientId(UUID patientId) {
        List<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByPatientIdAndDeletedFalseOrderByRecordDateDesc(patientId);
        return fluidBalanceMapper.toDTOList(fluidBalanceList);
    }

    public Page<FluidBalanceDTO> getFluidBalanceByPatientId(UUID patientId, Pageable pageable) {
        Page<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return fluidBalanceList.map(fluidBalanceMapper::toDTO);
    }

    public Page<FluidBalanceDTO> getFluidBalanceByShift(ShiftType shift, Pageable pageable) {
        Page<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByShiftAndDeletedFalse(shift, pageable);
        return fluidBalanceList.map(fluidBalanceMapper::toDTO);
    }

    public Page<FluidBalanceDTO> getFluidBalanceByPatientIdAndShift(UUID patientId, ShiftType shift, Pageable pageable) {
        Page<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByPatientIdAndShiftAndDeletedFalse(patientId, shift, pageable);
        return fluidBalanceList.map(fluidBalanceMapper::toDTO);
    }

    public List<FluidBalanceDTO> getFluidBalanceByDate(LocalDate date) {
        List<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByRecordDate(date);
        return fluidBalanceMapper.toDTOList(fluidBalanceList);
    }

    public Page<FluidBalanceDTO> getFluidBalanceByDateRange(UUID patientId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<FluidBalance> fluidBalanceList = fluidBalanceRepository.findByPatientIdAndDateRange(patientId, startDate, endDate, pageable);
        return fluidBalanceList.map(fluidBalanceMapper::toDTO);
    }

    public FluidBalanceDTO updateFluidBalance(UUID id, UpdateFluidBalanceRequest request) {
        FluidBalance fluidBalance = fluidBalanceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fluid Balance", "id", id));
        fluidBalanceMapper.updateEntityFromDTO(request, fluidBalance);
        fluidBalance = fluidBalanceRepository.save(fluidBalance);
        return fluidBalanceMapper.toDTO(fluidBalance);
    }

    public void deleteFluidBalance(UUID id) {
        FluidBalance fluidBalance = fluidBalanceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fluid Balance", "id", id));
        fluidBalance.setDeleted(true);
        fluidBalanceRepository.save(fluidBalance);
    }
}
