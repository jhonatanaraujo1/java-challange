package com.challange.orderjavachallange.unit.order.controller;

import com.challange.orderjavachallange.order.controller.OrderController;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;
import com.challange.orderjavachallange.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderController orderController;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setQuantity(5);
        request.setUserEmail("test@example.com");
        request.setUserName("Test User");

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(1L);
        response.setQuantity(5);

        when(orderService.create(any(OrderRequestDTO.class))).thenReturn(response);

        ResponseEntity<OrderResponseDTO> result = orderController.create(request);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(orderService, times(1)).create(any(OrderRequestDTO.class));
    }

    @Test
    void testFindOrderById() {
        Long orderId = 1L;

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(orderId);
        response.setQuantity(5);

        when(orderService.findById(orderId)).thenReturn(response);

        ResponseEntity<OrderResponseDTO> result = orderController.findOrderById(orderId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(orderService, times(1)).findById(orderId);
    }

    @Test
    void testFindOrderById_NotFound() {
        Long orderId = 99L;

        when(orderService.findById(orderId)).thenThrow(new RuntimeException("Order not found"));

        ResponseEntity<OrderResponseDTO> result = orderController.findOrderById(orderId);

        assertEquals(404, result.getStatusCodeValue());
        verify(orderService, times(1)).findById(orderId);
    }

    @Test
    void testUpdateOrder() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setId(1L);
        request.setQuantity(10);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(1L);
        response.setQuantity(10);

        when(orderService.update(any(OrderRequestDTO.class))).thenReturn(response);

        ResponseEntity<OrderResponseDTO> result = orderController.update(request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(orderService, times(1)).update(any(OrderRequestDTO.class));
    }

    @Test
    void testListAllOrders() {
        OrderResponseDTO response1 = new OrderResponseDTO();
        response1.setId(1L);
        response1.setQuantity(5);

        OrderResponseDTO response2 = new OrderResponseDTO();
        response2.setId(2L);
        response2.setQuantity(10);

        List<OrderResponseDTO> orders = List.of(response1, response2);

        when(orderService.findAll()).thenReturn(orders);

        ResponseEntity<List<OrderResponseDTO>> result = orderController.listAllOrders();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(orders, result.getBody());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void testListAllOrders_NoContent() {
        when(orderService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderResponseDTO>> result = orderController.listAllOrders();

        assertEquals(204, result.getStatusCodeValue());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        doNothing().when(orderService).delete(orderId);

        ResponseEntity<Void> result = orderController.delete(orderId);

        assertEquals(204, result.getStatusCodeValue());
        verify(orderService, times(1)).delete(orderId);
    }

    @Test
    void testDeleteOrder_NotFound() {
        Long orderId = 99L;

        doThrow(new RuntimeException("Order not found")).when(orderService).delete(orderId);

        ResponseEntity<Void> result = orderController.delete(orderId);

        assertEquals(404, result.getStatusCodeValue());
        verify(orderService, times(1)).delete(orderId);
    }
}
