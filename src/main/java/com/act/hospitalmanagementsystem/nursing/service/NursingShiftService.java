package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingShiftDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingShift;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
import com.act.hospitalmanagementsystem.nursing.mapper.NursingShiftMapper;
import com.act.hospitalmanagementsystem.nursing.repository.NursingShiftRepository;
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
public class NursingShiftService {

    private final NursingShiftRepository nursingShiftRepository;
    private final NursingShiftMapper nursingShiftMapper;

    public NursingShiftDTO createNursingShift(CreateNursingShiftRequest request) {
        NursingShift nursingShift = nursingShiftMapper.toEntity(request);
        nursingShift = nursingShiftRepository.save(nursingShift);
        return nursingShiftMapper.toDTO(nursingShift);
    }

    public NursingShiftDTO getNursingShiftById(UUID id) {
        NursingShift nursingShift = nursingShiftRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Shift", "id", id));
        return nursingShiftMapper.toDTO(nursingShift);
    }

    public List<NursingShiftDTO> getNursingShiftsByDate(LocalDate shiftDate) {
        List<NursingShift> nursingShifts = nursingShiftRepository.findByShiftDateAndDeletedFalseOrderByShiftDateDesc(shiftDate);
        return nursingShiftMapper.toDTOList(nursingShifts);
    }

    public Page<NursingShiftDTO> getNursingShiftsByDate(LocalDate shiftDate, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByShiftDateAndDeletedFalse(shiftDate, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public Page<NursingShiftDTO> getNursingShiftsByNurseId(UUID nurseId, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByNurseIdAndDeletedFalse(nurseId, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public Page<NursingShiftDTO> getNursingShiftsByType(ShiftType shiftType, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByShiftTypeAndDeletedFalse(shiftType, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public Page<NursingShiftDTO> getNursingShiftsByStatus(ShiftRecordStatus status, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByStatusAndDeletedFalse(status, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public Page<NursingShiftDTO> getNursingShiftsByUnit(String unit, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByUnitAndDeletedFalse(unit, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public Page<NursingShiftDTO> getNursingShiftsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<NursingShift> nursingShifts = nursingShiftRepository.findByDateRange(startDate, endDate, pageable);
        return nursingShifts.map(nursingShiftMapper::toDTO);
    }

    public NursingShiftDTO updateNursingShift(UUID id, UpdateNursingShiftRequest request) {
        NursingShift nursingShift = nursingShiftRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Shift", "id", id));
        nursingShiftMapper.updateEntityFromDTO(request, nursingShift);
        nursingShift = nursingShiftRepository.save(nursingShift);
        return nursingShiftMapper.toDTO(nursingShift);
    }

    public void deleteNursingShift(UUID id) {
        NursingShift nursingShift = nursingShiftRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Shift", "id", id));
        nursingShift.setDeleted(true);
        nursingShiftRepository.save(nursingShift);
    }
}
