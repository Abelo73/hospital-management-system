package com.act.hospitalmanagementsystem.inventory.dto;

import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {
    @NotBlank(message = "Item code is required")
    private String itemCode;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotNull(message = "Item type is required")
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

    private Boolean isControlledSubstance = false;

    private Boolean requiresPrescription = false;

    private Boolean isColdChain = false;

    private String imageUrl;

    private String specifications;
}
