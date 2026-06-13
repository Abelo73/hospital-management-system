package com.act.hospitalmanagementsystem.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {
    private String itemName;
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
}
