package com.act.hospitalmanagementsystem.inventory.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockDTO;
import com.act.hospitalmanagementsystem.inventory.entity.Stock;
import com.act.hospitalmanagementsystem.inventory.enums.StockStatus;
import com.act.hospitalmanagementsystem.inventory.mapper.StockMapper;
import com.act.hospitalmanagementsystem.inventory.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<StockDTO>> getStockStatus(String location, String category, String status, Pageable pageable) {
        try {
            Page<Stock> stocks;
            if (location != null && !location.equals("ALL")) {
                UUID locationId = UUID.fromString(location);
                stocks = stockRepository.findByLocationId(locationId, pageable);
            } else if (status != null) {
                stocks = stockRepository.findByStatus(StockStatus.valueOf(status), pageable);
            } else {
                stocks = stockRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(stockMapper.toDTOList(stocks.getContent()), "Stock status retrieved");
        } catch (Exception e) {
            log.error("Error getting stock status", e);
            return BaseResponseDTO.error("Failed to get stock status: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<StockDTO>> getStockByItem(UUID itemId) {
        try {
            Page<Stock> stocks = stockRepository.findByItemId(itemId, Pageable.unpaged());
            return BaseResponseDTO.success(stockMapper.toDTOList(stocks.getContent()), "Stock retrieved");
        } catch (Exception e) {
            log.error("Error getting stock by item", e);
            return BaseResponseDTO.error("Failed to get stock: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<StockDTO>> getLowStockItems(String location) {
        try {
            Page<Stock> stocks = stockRepository.findAllLowStock(Pageable.unpaged());
            return BaseResponseDTO.success(stockMapper.toDTOList(stocks.getContent()), "Low stock items retrieved");
        } catch (Exception e) {
            log.error("Error getting low stock items", e);
            return BaseResponseDTO.error("Failed to get low stock items: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<StockDTO>> getExpiringItems(Integer days, String location) {
        try {
            LocalDate date = LocalDate.now().plusDays(days);
            Page<Stock> stocks = stockRepository.findExpiringStock(date, Pageable.unpaged());
            return BaseResponseDTO.success(stockMapper.toDTOList(stocks.getContent()), "Expiring items retrieved");
        } catch (Exception e) {
            log.error("Error getting expiring items", e);
            return BaseResponseDTO.error("Failed to get expiring items: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> adjustStock(UUID itemId, UUID locationId, UUID batchId, 
            Integer adjustmentQuantity, String reason, String adjustmentType, String updatedBy) {
        try {
            Stock stock = stockRepository.findByItemIdAndLocationId(itemId, locationId)
                    .orElseThrow(() -> new RuntimeException("Stock not found"));

            if ("INCREASE".equals(adjustmentType)) {
                stock.setQuantity(stock.getQuantity() + adjustmentQuantity);
                stock.setAvailableQuantity(stock.getAvailableQuantity() + adjustmentQuantity);
            } else {
                if (stock.getAvailableQuantity() < adjustmentQuantity) {
                    return BaseResponseDTO.error("Insufficient stock for adjustment");
                }
                stock.setQuantity(stock.getQuantity() - adjustmentQuantity);
                stock.setAvailableQuantity(stock.getAvailableQuantity() - adjustmentQuantity);
            }

            stock.setUpdatedBy(updatedBy);
            stockRepository.save(stock);

            // TODO: Log the adjustment for audit trail

            return BaseResponseDTO.success(null, "Stock adjusted successfully");
        } catch (Exception e) {
            log.error("Error adjusting stock", e);
            return BaseResponseDTO.error("Failed to adjust stock: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> transferStock(UUID itemId, UUID fromLocationId, UUID toLocationId, 
            Integer quantity, String reason, String updatedBy) {
        try {
            Stock fromStock = stockRepository.findByItemIdAndLocationId(itemId, fromLocationId)
                    .orElseThrow(() -> new RuntimeException("Source stock not found"));

            if (fromStock.getAvailableQuantity() < quantity) {
                return BaseResponseDTO.error("Insufficient stock for transfer");
            }

            // Deduct from source
            fromStock.setQuantity(fromStock.getQuantity() - quantity);
            fromStock.setAvailableQuantity(fromStock.getAvailableQuantity() - quantity);
            fromStock.setUpdatedBy(updatedBy);
            stockRepository.save(fromStock);

            // Add to destination
            Stock toStock = stockRepository.findByItemIdAndLocationId(itemId, toLocationId)
                    .orElse(new Stock());
            if (toStock.getId() == null) {
                toStock.setItem(fromStock.getItem());
                toStock.setLocation(fromStock.getLocation());
                toStock.setBatch(fromStock.getBatch());
                toStock.setQuantity(quantity);
                toStock.setAvailableQuantity(quantity);
                toStock.setCreatedBy(updatedBy);
            } else {
                toStock.setQuantity(toStock.getQuantity() + quantity);
                toStock.setAvailableQuantity(toStock.getAvailableQuantity() + quantity);
                toStock.setUpdatedBy(updatedBy);
            }
            stockRepository.save(toStock);

            // TODO: Log the transfer for audit trail

            return BaseResponseDTO.success(null, "Stock transferred successfully");
        } catch (Exception e) {
            log.error("Error transferring stock", e);
            return BaseResponseDTO.error("Failed to transfer stock: " + e.getMessage());
        }
    }
}
