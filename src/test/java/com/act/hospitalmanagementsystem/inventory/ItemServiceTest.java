package com.act.hospitalmanagementsystem.inventory;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.CreateItemRequest;
import com.act.hospitalmanagementsystem.inventory.dto.ItemDTO;
import com.act.hospitalmanagementsystem.inventory.entity.Item;
import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import com.act.hospitalmanagementsystem.inventory.mapper.ItemMapper;
import com.act.hospitalmanagementsystem.inventory.repository.ItemRepository;
import com.act.hospitalmanagementsystem.inventory.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private Item testItem;
    private ItemDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setId(UUID.randomUUID());
        testItem.setItemCode("ITM001");
        testItem.setItemName("Test Drug");
        testItem.setDeleted(false);

        testItemDTO = new ItemDTO();
        testItemDTO.setId(testItem.getId());
        testItemDTO.setItemCode(testItem.getItemCode());
        testItemDTO.setItemName(testItem.getItemName());
    }

    @Test
    void createItem_Success() {
        CreateItemRequest request = new CreateItemRequest();
        request.setItemCode("ITM001");

        when(itemRepository.findByItemCode(anyString())).thenReturn(Optional.empty());
        when(itemMapper.toEntity(any())).thenReturn(testItem);
        when(itemRepository.save(any())).thenReturn(testItem);
        when(itemMapper.toDTO(any())).thenReturn(testItemDTO);

        BaseResponseDTO<ItemDTO> response = itemService.createItem(request, "admin");

        assertTrue(response.isSuccess());
        assertEquals("ITM001", response.getData().getItemCode());
        verify(itemRepository).save(any());
    }

    @Test
    void createItem_Failure_DuplicateCode() {
        CreateItemRequest request = new CreateItemRequest();
        request.setItemCode("ITM001");

        when(itemRepository.findByItemCode("ITM001")).thenReturn(Optional.of(testItem));

        BaseResponseDTO<ItemDTO> response = itemService.createItem(request, "admin");

        assertFalse(response.isSuccess());
        assertEquals("Item code already exists", response.getMessage());
    }

    @Test
    void deleteItem_Success_SoftDelete() {
        UUID id = testItem.getId();
        when(itemRepository.findById(id)).thenReturn(Optional.of(testItem));

        itemService.deleteItem(id);

        assertTrue(testItem.getDeleted());
        verify(itemRepository).save(testItem);
    }

    @Test
    void getItemById_Success() {
        UUID id = testItem.getId();
        when(itemRepository.findById(id)).thenReturn(Optional.of(testItem));
        when(itemMapper.toDTO(testItem)).thenReturn(testItemDTO);

        BaseResponseDTO<ItemDTO> response = itemService.getItemById(id);

        assertTrue(response.isSuccess());
        assertEquals(testItemDTO, response.getData());
    }
}
