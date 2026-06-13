package com.act.hospitalmanagementsystem.inventory.mapper;

import com.act.hospitalmanagementsystem.inventory.dto.CreateItemRequest;
import com.act.hospitalmanagementsystem.inventory.dto.ItemDTO;
import com.act.hospitalmanagementsystem.inventory.dto.UpdateItemRequest;
import com.act.hospitalmanagementsystem.inventory.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

    public ItemDTO toDTO(Item item) {
        if (item == null) {
            return null;
        }

        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setItemCode(item.getItemCode());
        dto.setItemName(item.getItemName());
        dto.setItemType(item.getItemType());
        dto.setCategory(item.getCategory());
        dto.setDescription(item.getDescription());
        dto.setManufacturer(item.getManufacturer());
        dto.setBrand(item.getBrand());
        dto.setModel(item.getModel());
        dto.setUnitOfMeasure(item.getUnitOfMeasure());
        dto.setPackSize(item.getPackSize());
        dto.setMinimumOrderQuantity(item.getMinimumOrderQuantity());
        dto.setReorderLevel(item.getReorderLevel());
        dto.setSafetyStock(item.getSafetyStock());
        dto.setMaximumStock(item.getMaximumStock());
        dto.setLeadTimeDays(item.getLeadTimeDays());
        dto.setShelfLifeDays(item.getShelfLifeDays());
        dto.setStorageConditions(item.getStorageConditions());
        dto.setIsControlledSubstance(item.getIsControlledSubstance());
        dto.setRequiresPrescription(item.getRequiresPrescription());
        dto.setIsColdChain(item.getIsColdChain());
        dto.setImageUrl(item.getImageUrl());
        dto.setSpecifications(item.getSpecifications());
        dto.setIsActive(item.getIsActive());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setCreatedBy(item.getCreatedBy());
        return dto;
    }

    public Item toEntity(CreateItemRequest request) {
        if (request == null) {
            return null;
        }

        Item item = new Item();
        item.setItemCode(request.getItemCode());
        item.setItemName(request.getItemName());
        item.setItemType(request.getItemType());
        item.setCategory(request.getCategory());
        item.setDescription(request.getDescription());
        item.setManufacturer(request.getManufacturer());
        item.setBrand(request.getBrand());
        item.setModel(request.getModel());
        item.setUnitOfMeasure(request.getUnitOfMeasure());
        item.setPackSize(request.getPackSize());
        item.setMinimumOrderQuantity(request.getMinimumOrderQuantity());
        item.setReorderLevel(request.getReorderLevel());
        item.setSafetyStock(request.getSafetyStock());
        item.setMaximumStock(request.getMaximumStock());
        item.setLeadTimeDays(request.getLeadTimeDays());
        item.setShelfLifeDays(request.getShelfLifeDays());
        item.setStorageConditions(request.getStorageConditions());
        item.setIsControlledSubstance(request.getIsControlledSubstance());
        item.setRequiresPrescription(request.getRequiresPrescription());
        item.setIsColdChain(request.getIsColdChain());
        item.setImageUrl(request.getImageUrl());
        item.setSpecifications(request.getSpecifications());
        item.setIsActive(true);
        return item;
    }

    public void updateEntityFromDTO(Item item, UpdateItemRequest request) {
        if (request == null || item == null) {
            return;
        }

        if (request.getItemName() != null) {
            item.setItemName(request.getItemName());
        }
        if (request.getCategory() != null) {
            item.setCategory(request.getCategory());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }
        if (request.getManufacturer() != null) {
            item.setManufacturer(request.getManufacturer());
        }
        if (request.getBrand() != null) {
            item.setBrand(request.getBrand());
        }
        if (request.getModel() != null) {
            item.setModel(request.getModel());
        }
        if (request.getUnitOfMeasure() != null) {
            item.setUnitOfMeasure(request.getUnitOfMeasure());
        }
        if (request.getPackSize() != null) {
            item.setPackSize(request.getPackSize());
        }
        if (request.getMinimumOrderQuantity() != null) {
            item.setMinimumOrderQuantity(request.getMinimumOrderQuantity());
        }
        if (request.getReorderLevel() != null) {
            item.setReorderLevel(request.getReorderLevel());
        }
        if (request.getSafetyStock() != null) {
            item.setSafetyStock(request.getSafetyStock());
        }
        if (request.getMaximumStock() != null) {
            item.setMaximumStock(request.getMaximumStock());
        }
        if (request.getLeadTimeDays() != null) {
            item.setLeadTimeDays(request.getLeadTimeDays());
        }
        if (request.getShelfLifeDays() != null) {
            item.setShelfLifeDays(request.getShelfLifeDays());
        }
        if (request.getStorageConditions() != null) {
            item.setStorageConditions(request.getStorageConditions());
        }
        if (request.getIsControlledSubstance() != null) {
            item.setIsControlledSubstance(request.getIsControlledSubstance());
        }
        if (request.getRequiresPrescription() != null) {
            item.setRequiresPrescription(request.getRequiresPrescription());
        }
        if (request.getIsColdChain() != null) {
            item.setIsColdChain(request.getIsColdChain());
        }
        if (request.getImageUrl() != null) {
            item.setImageUrl(request.getImageUrl());
        }
        if (request.getSpecifications() != null) {
            item.setSpecifications(request.getSpecifications());
        }
        if (request.getIsActive() != null) {
            item.setIsActive(request.getIsActive());
        }
    }

    public List<ItemDTO> toDTOList(List<Item> items) {
        return items.stream().map(this::toDTO).toList();
    }
}
