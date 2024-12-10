package com.challange.orderjavachallange.unit.stockmovement.service;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.repository.ItemRepository;
import com.challange.orderjavachallange.order.repository.OrderRepository;
import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.stockmovement.mapper.StockMovementMapper;
import com.challange.orderjavachallange.stockmovement.repository.StockMovementRepository;
import com.challange.orderjavachallange.config.EmailService;
import com.challange.orderjavachallange.stockmovement.service.StockMovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StockMovementServiceTest {

    private StockMovementService stockMovementService;
    private StockMovementRepository stockMovementRepository;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        stockMovementRepository = mock(StockMovementRepository.class);
        orderRepository = mock(OrderRepository.class);
        itemRepository = mock(ItemRepository.class);
        emailService = mock(EmailService.class);

        stockMovementService = new StockMovementService(
                stockMovementRepository,
                null,
                orderRepository,
                itemRepository,
                null,
                emailService
        );
    }

    @Test
    void testCreateStockMovement_Success() {
        StockMovementDto dto = new StockMovementDto();
        dto.setItemId(1L);
        dto.setQuantity(50);

        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("Item A");

        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setItem(item);
        stockMovement.setQuantity(50);

        when(itemRepository.findById(dto.getItemId())).thenReturn(Optional.of(item));
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(stockMovement);

        StockMovementDto response = stockMovementService.createStockMovement(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(50, response.getQuantity());
        assertEquals(1L, response.getItemId());

        verify(itemRepository, times(1)).findById(dto.getItemId());
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }

    @Test
    void testCreateStockMovement_ItemNotFound() {
        StockMovementDto dto = new StockMovementDto();
        dto.setItemId(1L);

        when(itemRepository.findById(dto.getItemId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> stockMovementService.createStockMovement(dto));

        assertEquals("Item not found with ID: 1", exception.getMessage());
        verify(itemRepository, times(1)).findById(dto.getItemId());
        verifyNoInteractions(stockMovementRepository);
    }

    @Test
    void testAssignStockToPendingOrders_NoPendingOrders() {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("Item A");

        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setItem(item);
        stockMovement.setQuantity(50);

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(orderRepository.findPendingOrdersByItem(item)).thenReturn(List.of());
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(stockMovement);

        stockMovementService.createStockMovement(StockMovementMapper.toDto(stockMovement));

        verify(orderRepository, times(1)).findPendingOrdersByItem(item);
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
        verifyNoInteractions(emailService);
    }

}
