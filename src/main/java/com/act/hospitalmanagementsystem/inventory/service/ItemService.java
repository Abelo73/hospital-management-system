package com.act.hospitalmanagementsystem.inventory.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.CreateItemRequest;
import com.act.hospitalmanagementsystem.inventory.dto.ItemDTO;
import com.act.hospitalmanagementsystem.inventory.dto.UpdateItemRequest;
import com.act.hospitalmanagementsystem.inventory.entity.Item;
import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import com.act.hospitalmanagementsystem.inventory.mapper.ItemMapper;
import com.act.hospitalmanagementsystem.inventory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Transactional
    public BaseResponseDTO<ItemDTO> createItem(CreateItemRequest request, String createdBy) {
        try {
            if (itemRepository.findByItemCode(request.getItemCode()).isPresent()) {
                return BaseResponseDTO.error("Item code already exists");
            }

            Item item = itemMapper.toEntity(request);
            item.setCreatedBy(createdBy);
            Item saved = itemRepository.save(item);

            return BaseResponseDTO.success(itemMapper.toDTO(saved), "Item created successfully");
        } catch (Exception e) {
            log.error("Error creating item", e);
            return BaseResponseDTO.error("Failed to create item: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<ItemDTO>> getAllItems(String category, ItemType itemType, String status, Pageable pageable) {
        try {
            Page<Item> items;
            if (category != null) {
                items = itemRepository.findByCategory(category, pageable);
            } else if (itemType != null) {
                items = itemRepository.findByItemType(itemType, pageable);
            } else if ("ACTIVE".equals(status)) {
                items = itemRepository.findByIsActiveTrue(pageable);
            } else {
                items = itemRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(itemMapper.toDTOList(items.getContent()), "Items retrieved");
        } catch (Exception e) {
            log.error("Error getting items", e);
            return BaseResponseDTO.error("Failed to get items: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<ItemDTO> getItemById(UUID id) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            return BaseResponseDTO.success(itemMapper.toDTO(item), "Item retrieved");
        } catch (Exception e) {
            log.error("Error getting item", e);
            return BaseResponseDTO.error("Failed to get item: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<ItemDTO> getItemByCode(String itemCode) {
        try {
            Item item = itemRepository.findByItemCode(itemCode)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            return BaseResponseDTO.success(itemMapper.toDTO(item), "Item retrieved");
        } catch (Exception e) {
            log.error("Error getting item by code", e);
            return BaseResponseDTO.error("Failed to get item: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<ItemDTO> updateItem(UUID id, UpdateItemRequest request, String updatedBy) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            itemMapper.updateEntityFromDTO(item, request);
            item.setUpdatedBy(updatedBy);
            Item saved = itemRepository.save(item);

            return BaseResponseDTO.success(itemMapper.toDTO(saved), "Item updated successfully");
        } catch (Exception e) {
            log.error("Error updating item", e);
            return BaseResponseDTO.error("Failed to update item: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteItem(UUID id) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            item.setDeleted(true);
            itemRepository.save(item);
            return BaseResponseDTO.success(null, "Item deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting item", e);
            return BaseResponseDTO.error("Failed to delete item: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<ItemDTO>> searchItems(String query, Pageable pageable) {
        try {
            Page<Item> items = itemRepository.searchItems(query, pageable);
            return BaseResponseDTO.success(itemMapper.toDTOList(items.getContent()), "Items retrieved");
        } catch (Exception e) {
            log.error("Error searching items", e);
            return BaseResponseDTO.error("Failed to search items: " + e.getMessage());
        }
    }
}
