package com.act.hospitalmanagementsystem.medical.mapper;

import com.act.hospitalmanagementsystem.medical.dto.AllergyDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.entity.Allergy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AllergyMapper {
    AllergyDTO toDTO(Allergy allergy);
    List<AllergyDTO> toDTOList(List<Allergy> allergies);
    Allergy toEntity(CreateAllergyRequest request);
    void updateEntityFromDTO(UpdateAllergyRequest request, @MappingTarget Allergy allergy);
}
