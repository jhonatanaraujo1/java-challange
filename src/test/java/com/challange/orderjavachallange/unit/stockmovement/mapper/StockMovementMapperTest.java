package com.challange.orderjavachallange.unit.stockmovement.mapper;

import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.stockmovement.mapper.StockMovementMapper;
import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.order.entity.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StockMovementMapperTest {

    @Test
    void testToDto_WithValidStockMovement() {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("Item A");

        Order order = new Order();
        order.setId(1L);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setCreationDate(LocalDateTime.now());
        stockMovement.setQuantity(100);
        stockMovement.setItem(item);
        stockMovement.setOrder(order);

        StockMovementDto dto = StockMovementMapper.toDto(stockMovement);

        assertNotNull(dto);
        assertEquals(stockMovement.getId(), dto.getId());
        assertEquals(stockMovement.getQuantity(), dto.getQuantity());
        assertEquals(stockMovement.getItem().getId(), dto.getItemId());
        assertEquals(stockMovement.getItem().getName(), dto.getItemName());
        assertEquals(stockMovement.getOrder().getId(), dto.getOrderId());
    }

    @Test
    void testToDto_WithNullStockMovement() {
        StockMovementDto dto = StockMovementMapper.toDto(null);

        assertNull(dto);
    }

    @Test
    void testToEntity_WithValidDto() {
        StockMovementDto dto = new StockMovementDto();
        dto.setId(1L);
        dto.setQuantity(100);
        dto.setItemId(1L);

        StockMovement stockMovement = StockMovementMapper.toEntity(dto);

        assertNotNull(stockMovement);
        assertEquals(dto.getId(), stockMovement.getId());
        assertEquals(dto.getQuantity(), stockMovement.getQuantity());
    }

    @Test
    void testToEntity_WithNullDto() {
        StockMovement stockMovement = StockMovementMapper.toEntity(null);

        assertNull(stockMovement);
    }
}
