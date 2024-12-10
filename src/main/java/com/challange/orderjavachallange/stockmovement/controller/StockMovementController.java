package com.challange.orderjavachallange.stockmovement.controller;

import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.service.StockMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock-movements")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementDto> create(@RequestBody StockMovementDto order) {
        try {
            StockMovementDto response = stockMovementService.createStockMovement(order);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

