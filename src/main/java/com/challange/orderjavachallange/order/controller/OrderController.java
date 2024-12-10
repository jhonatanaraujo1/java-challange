package com.challange.orderjavachallange.order.controller;

import com.challange.orderjavachallange.order.service.OrderService;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO order) {
        try {
            OrderResponseDTO response = orderService.create(order);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findOrderById(@PathVariable Long id) {
        try {
            OrderResponseDTO response = orderService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<OrderResponseDTO> update(@RequestBody OrderRequestDTO order) {
        try {
            OrderResponseDTO response = orderService.update(order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> listAllOrders() {
        List<OrderResponseDTO> orders = orderService.findAll();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

