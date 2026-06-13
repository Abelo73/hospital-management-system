package com.act.hospitalmanagementsystem.inventory.mapper;

import com.act.hospitalmanagementsystem.inventory.dto.StockDTO;
import com.act.hospitalmanagementsystem.inventory.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockMapper {

    public StockDTO toDTO(Stock stock) {
        if (stock == null) {
            return null;
        }

        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setItemId(stock.getItem() != null ? stock.getItem().getId() : null);
        dto.setLocationId(stock.getLocation() != null ? stock.getLocation().getId() : null);
        dto.setBatchId(stock.getBatch() != null ? stock.getBatch().getId() : null);
        dto.setQuantity(stock.getQuantity());
        dto.setAvailableQuantity(stock.getAvailableQuantity());
        dto.setReservedQuantity(stock.getReservedQuantity());
        dto.setUnitCost(stock.getUnitCost());
        dto.setTotalCost(stock.getTotalCost());
        dto.setExpiryDate(stock.getExpiryDate());
        dto.setManufactureDate(stock.getManufactureDate());
        dto.setLastReceivedDate(stock.getLastReceivedDate());
        dto.setLastIssuedDate(stock.getLastIssuedDate());
        dto.setStatus(stock.getStatus());
        dto.setCreatedAt(stock.getCreatedAt());
        dto.setCreatedBy(stock.getCreatedBy());
        return dto;
    }

    public List<StockDTO> toDTOList(List<Stock> stocks) {
        return stocks.stream().map(this::toDTO).toList();
    }
}
