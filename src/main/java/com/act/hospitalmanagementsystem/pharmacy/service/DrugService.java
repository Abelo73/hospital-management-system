package com.act.hospitalmanagementsystem.pharmacy.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DrugDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DrugInteractionCheckDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Drug;
import com.act.hospitalmanagementsystem.pharmacy.entity.DrugInteraction;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugCategory;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugSchedule;
import com.act.hospitalmanagementsystem.pharmacy.mapper.DrugMapper;
import com.act.hospitalmanagementsystem.pharmacy.repository.DrugInteractionRepository;
import com.act.hospitalmanagementsystem.pharmacy.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository drugRepository;
    private final DrugInteractionRepository drugInteractionRepository;
    private final DrugMapper drugMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<DrugDTO>> searchDrugs(String query, Pageable pageable) {
        try {
            Page<Drug> drugs = drugRepository.searchDrugs(query, pageable);
            return BaseResponseDTO.success(drugMapper.toDTOList(drugs.getContent()), "Drugs retrieved");
        } catch (Exception e) {
            log.error("Error searching drugs", e);
            return BaseResponseDTO.error("Failed to search drugs: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<DrugDTO> getDrugDetails(UUID drugId) {
        try {
            Drug drug = drugRepository.findById(drugId)
                    .orElseThrow(() -> new RuntimeException("Drug not found"));
            return BaseResponseDTO.success(drugMapper.toDTO(drug), "Drug details retrieved");
        } catch (Exception e) {
            log.error("Error getting drug details", e);
            return BaseResponseDTO.error("Failed to get drug details: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<DrugInteractionCheckDTO> checkDrugInteractions(List<String> drugs, UUID patientId) {
        try {
            // TODO: Implement drug interaction checking
            // TODO: Check for patient allergies
            return BaseResponseDTO.success(new DrugInteractionCheckDTO(false, List.of(), List.of()), "Interaction check completed");
        } catch (Exception e) {
            log.error("Error checking drug interactions", e);
            return BaseResponseDTO.error("Failed to check interactions: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<DrugDTO>> getControlledSubstances(Pageable pageable) {
        try {
            Page<Drug> drugs = drugRepository.findControlledSubstances(pageable);
            return BaseResponseDTO.success(drugMapper.toDTOList(drugs.getContent()), "Controlled substances retrieved");
        } catch (Exception e) {
            log.error("Error getting controlled substances", e);
            return BaseResponseDTO.error("Failed to get controlled substances: " + e.getMessage());
        }
    }
}
