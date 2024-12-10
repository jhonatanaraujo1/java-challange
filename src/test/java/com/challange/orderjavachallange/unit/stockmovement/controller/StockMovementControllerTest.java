package com.challange.orderjavachallange.unit.stockmovement.controller;

import com.challange.orderjavachallange.stockmovement.controller.StockMovementController;
import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.service.StockMovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMovementControllerTest {

    private StockMovementController stockMovementController;
    private StockMovementService stockMovementService;

    @BeforeEach
    void setUp() {
        stockMovementService = mock(StockMovementService.class);
        stockMovementController = new StockMovementController(stockMovementService);
    }

    @Test
    void testCreateStockMovement_Success() {
        StockMovementDto requestDto = new StockMovementDto();
        requestDto.setItemId(1L);
        requestDto.setQuantity(100);

        StockMovementDto responseDto = new StockMovementDto();
        responseDto.setId(1L);
        responseDto.setItemId(1L);
        responseDto.setQuantity(100);

        when(stockMovementService.createStockMovement(any(StockMovementDto.class))).thenReturn(responseDto);

        ResponseEntity<StockMovementDto> response = stockMovementController.create(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDto, response.getBody());

        verify(stockMovementService, times(1)).createStockMovement(any(StockMovementDto.class));
    }

    @Test
    void testCreateStockMovement_BadRequest() {
        StockMovementDto requestDto = new StockMovementDto();
        requestDto.setItemId(1L);
        requestDto.setQuantity(100);

        when(stockMovementService.createStockMovement(any(StockMovementDto.class)))
                .thenThrow(new RuntimeException("Error creating stock movement"));

        ResponseEntity<StockMovementDto> response = stockMovementController.create(requestDto);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(stockMovementService, times(1)).createStockMovement(any(StockMovementDto.class));
    }
}
