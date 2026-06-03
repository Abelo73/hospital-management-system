package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingNoteDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingNoteRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingNote;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NursingNoteMapper {
    NursingNoteDTO toDTO(NursingNote nursingNote);
    List<NursingNoteDTO> toDTOList(List<NursingNote> nursingNotes);
    NursingNote toEntity(CreateNursingNoteRequest request);
    void updateEntityFromDTO(UpdateNursingNoteRequest request, @MappingTarget NursingNote nursingNote);
}
