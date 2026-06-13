package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.CreateItemRequest;
import com.act.hospitalmanagementsystem.inventory.dto.ItemDTO;
import com.act.hospitalmanagementsystem.inventory.dto.UpdateItemRequest;
import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import com.act.hospitalmanagementsystem.inventory.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<ItemDTO>>> getAllItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ItemType itemType,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        BaseResponseDTO<List<ItemDTO>> response = itemService.getAllItems(category, itemType, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<ItemDTO>> getItemById(@PathVariable UUID id) {
        BaseResponseDTO<ItemDTO> response = itemService.getItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{itemCode}")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<ItemDTO>> getItemByCode(@PathVariable String itemCode) {
        BaseResponseDTO<ItemDTO> response = itemService.getItemByCode(itemCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<ItemDTO>> createItem(
            @Valid @RequestBody CreateItemRequest request,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<ItemDTO> response = itemService.createItem(request, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<ItemDTO>> updateItem(
            @PathVariable UUID id,
            @RequestBody UpdateItemRequest request,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<ItemDTO> response = itemService.updateItem(id, request, updatedBy);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteItem(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = itemService.deleteItem(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<ItemDTO>>> searchItems(
            @RequestParam String query,
            Pageable pageable) {
        BaseResponseDTO<List<ItemDTO>> response = itemService.searchItems(query, pageable);
        return ResponseEntity.ok(response);
    }
}
