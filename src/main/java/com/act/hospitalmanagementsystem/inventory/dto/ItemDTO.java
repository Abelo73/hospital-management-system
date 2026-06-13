package com.act.hospitalmanagementsystem.inventory.dto;

import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private UUID id;
    private String itemCode;
    private String itemName;
    private ItemType itemType;
    private String category;
    private String description;
    private String manufacturer;
    private String brand;
    private String model;
    private String unitOfMeasure;
    private Integer packSize;
    private Integer minimumOrderQuantity;
    private Integer reorderLevel;
    private Integer safetyStock;
    private Integer maximumStock;
    private Integer leadTimeDays;
    private Integer shelfLifeDays;
    private String storageConditions;
    private Boolean isControlledSubstance;
    private Boolean requiresPrescription;
    private Boolean isColdChain;
    private String imageUrl;
    private String specifications;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String createdBy;
}
