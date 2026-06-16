package com.act.hospitalmanagementsystem.inventory;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.entity.Item;
import com.act.hospitalmanagementsystem.inventory.entity.Location;
import com.act.hospitalmanagementsystem.inventory.entity.Stock;
import com.act.hospitalmanagementsystem.inventory.mapper.StockMapper;
import com.act.hospitalmanagementsystem.inventory.repository.LocationRepository;
import com.act.hospitalmanagementsystem.inventory.repository.StockRepository;
import com.act.hospitalmanagementsystem.inventory.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockService stockService;

    private Stock sourceStock;
    private Location destLocation;
    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setId(UUID.randomUUID());

        sourceStock = new Stock();
        sourceStock.setId(UUID.randomUUID());
        sourceStock.setItem(testItem);
        sourceStock.setQuantity(100);
        sourceStock.setAvailableQuantity(100);

        destLocation = new Location();
        destLocation.setId(UUID.randomUUID());
        destLocation.setLocationName("Pharmacy");
    }

    @Test
    void transferStock_Success_NewDestinationRecord() {
        UUID itemId = testItem.getId();
        UUID fromId = UUID.randomUUID();
        UUID toId = destLocation.getId();

        when(stockRepository.findByItemIdAndLocationId(itemId, fromId)).thenReturn(Optional.of(sourceStock));
        // Destination stock doesn't exist yet
        when(stockRepository.findByItemIdAndLocationId(itemId, toId)).thenReturn(Optional.empty());
        when(locationRepository.findById(toId)).thenReturn(Optional.of(destLocation));

        BaseResponseDTO<Void> response = stockService.transferStock(itemId, fromId, toId, 30, "Transfer", "admin");

        assertTrue(response.isSuccess());
        assertEquals(70, sourceStock.getQuantity());
        
        // Verify that a new stock record is saved with the CORRECT destination location
        verify(stockRepository, times(2)).save(any(Stock.class));
        
        // This is the key check for the bug I fixed
        verify(locationRepository).findById(toId);
    }

    @Test
    void transferStock_Failure_InsufficientStock() {
        UUID itemId = testItem.getId();
        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        when(stockRepository.findByItemIdAndLocationId(itemId, fromId)).thenReturn(Optional.of(sourceStock));

        BaseResponseDTO<Void> response = stockService.transferStock(itemId, fromId, toId, 150, "Transfer", "admin");

        assertFalse(response.isSuccess());
        assertEquals("Insufficient stock for transfer", response.getMessage());
    }
}
