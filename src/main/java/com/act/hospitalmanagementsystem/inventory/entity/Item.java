package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory_items")
public class Item extends BaseEntity {

    @Column(name = "item_code", unique = true, nullable = false)
    private String itemCode;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "category")
    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "unit_of_measure", length = 20)
    private String unitOfMeasure;

    @Column(name = "pack_size")
    private Integer packSize;

    @Column(name = "minimum_order_quantity")
    private Integer minimumOrderQuantity;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "safety_stock")
    private Integer safetyStock;

    @Column(name = "maximum_stock")
    private Integer maximumStock;

    @Column(name = "lead_time_days")
    private Integer leadTimeDays;

    @Column(name = "shelf_life_days")
    private Integer shelfLifeDays;

    @Column(name = "storage_conditions", columnDefinition = "TEXT")
    private String storageConditions;

    @Column(name = "is_controlled_substance", nullable = false)
    private Boolean isControlledSubstance = false;

    @Column(name = "requires_prescription", nullable = false)
    private Boolean requiresPrescription = false;

    @Column(name = "is_cold_chain", nullable = false)
    private Boolean isColdChain = false;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "specifications", columnDefinition = "JSON")
    private String specifications;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
