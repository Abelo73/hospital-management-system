package com.act.hospitalmanagementsystem.inventory;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockIssueDTO;
import com.act.hospitalmanagementsystem.inventory.entity.DepartmentRequest;
import com.act.hospitalmanagementsystem.inventory.entity.StockIssue;
import com.act.hospitalmanagementsystem.inventory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.inventory.mapper.DistributionMapper;
import com.act.hospitalmanagementsystem.inventory.repository.DistributionRepository;
import com.act.hospitalmanagementsystem.inventory.service.DistributionService;
import com.act.hospitalmanagementsystem.inventory.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistributionServiceTest {

    @Mock
    private DistributionRepository distributionRepository;

    @Mock
    private DistributionMapper distributionMapper;

    @Mock
    private StockService stockService;

    @InjectMocks
    private DistributionService distributionService;

    private DepartmentRequest testRequest;
    private List<Map<String, Object>> testItems;

    @BeforeEach
    void setUp() {
        testRequest = new DepartmentRequest();
        testRequest.setId(UUID.randomUUID());
        testRequest.setStatus(RequestStatus.APPROVED);
        testRequest.setDepartment("Emergency");

        testItems = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("itemId", UUID.randomUUID().toString());
        item.put("locationId", UUID.randomUUID().toString());
        item.put("quantity", 50);
        testItems.add(item);
    }

    @Test
    void issueStock_Success_TriggersStockDeduction() {
        UUID requestId = testRequest.getId();
        UUID userId = UUID.randomUUID();

        when(distributionRepository.findDepartmentRequestById(requestId)).thenReturn(Optional.of(testRequest));
        when(distributionRepository.saveStockIssue(any(StockIssue.class))).thenAnswer(i -> i.getArguments()[0]);
        when(distributionMapper.toDTO(any(StockIssue.class))).thenReturn(new StockIssueDTO());

        BaseResponseDTO<StockIssueDTO> response = distributionService.issueStock(requestId, testItems, userId);

        assertTrue(response.isSuccess());
        assertEquals(RequestStatus.ISSUED, testRequest.getStatus());
        
        // Verify that stockService.adjustStock was called for each item
        verify(stockService, times(1)).adjustStock(
            any(UUID.class), 
            any(UUID.class), 
            any(), 
            eq(50), 
            anyString(), 
            eq("DECREASE"), 
            anyString()
        );
    }

    @Test
    void issueStock_Failure_NotApproved() {
        testRequest.setStatus(RequestStatus.PENDING);
        when(distributionRepository.findDepartmentRequestById(testRequest.getId())).thenReturn(Optional.of(testRequest));

        BaseResponseDTO<StockIssueDTO> response = distributionService.issueStock(testRequest.getId(), testItems, UUID.randomUUID());

        assertFalse(response.isSuccess());
        assertEquals("Request must be approved before issuing stock", response.getMessage());
        verify(stockService, never()).adjustStock(any(), any(), any(), any(), any(), any(), any());
    }
}
