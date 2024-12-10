package com.challange.orderjavachallange.stockmovement.mapper;

import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;

public class StockMovementMapper {

    public static StockMovementDto toDto(StockMovement stockMovement) {
        if (stockMovement == null) {
            return null;
        }
        return new StockMovementDto(
                stockMovement.getId(),
                stockMovement.getCreationDate(),
                stockMovement.getQuantity(),
                stockMovement.getItem().getId(),
                stockMovement.getItem().getName(),
                stockMovement.getOrder() != null ? stockMovement.getOrder().getId() : null
        );
    }

    public static StockMovement toEntity(StockMovementDto dto) {
        if (dto == null) {
            return null;
        }
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(dto.getId());
        stockMovement.setQuantity(dto.getQuantity());
        return stockMovement;
    }
}
