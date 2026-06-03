package com.act.hospitalmanagementsystem.nursing.mapper;

import com.act.hospitalmanagementsystem.nursing.dto.CreateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.dto.FluidBalanceDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateFluidBalanceRequest;
import com.act.hospitalmanagementsystem.nursing.entity.FluidBalance;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FluidBalanceMapper {
    FluidBalanceDTO toDTO(FluidBalance fluidBalance);
    List<FluidBalanceDTO> toDTOList(List<FluidBalance> fluidBalanceList);
    FluidBalance toEntity(CreateFluidBalanceRequest request);
    void updateEntityFromDTO(UpdateFluidBalanceRequest request, @MappingTarget FluidBalance fluidBalance);
}
