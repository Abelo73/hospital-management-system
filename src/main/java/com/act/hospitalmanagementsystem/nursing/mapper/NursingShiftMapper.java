package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingShiftDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingShiftRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingShift;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NursingShiftMapper {
    NursingShiftDTO toDTO(NursingShift nursingShift);
    List<NursingShiftDTO> toDTOList(List<NursingShift> nursingShifts);
    NursingShift toEntity(CreateNursingShiftRequest request);
    void updateEntityFromDTO(UpdateNursingShiftRequest request, @MappingTarget NursingShift nursingShift);
}
