package com.challange.orderjavachallange.unit.order.service;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.repository.ItemRepository;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;
import com.challange.orderjavachallange.order.entity.Order;
import com.challange.orderjavachallange.order.repository.OrderRepository;
import com.challange.orderjavachallange.order.service.OrderService;
import com.challange.orderjavachallange.stockmovement.repository.StockMovementRepository;
import com.challange.orderjavachallange.user.entity.User;
import com.challange.orderjavachallange.user.repository.UserRepository;
import com.challange.orderjavachallange.config.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private StockMovementRepository stockMovementRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        itemRepository = mock(ItemRepository.class);
        stockMovementRepository = mock(StockMovementRepository.class);
        userRepository = mock(UserRepository.class);
        emailService = mock(EmailService.class);

        orderService = new OrderService(orderRepository, itemRepository, stockMovementRepository, userRepository, emailService);
    }

    @Test
    void testCreateOrder_Success() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setItemName("Item A");
        request.setSkuCode("SKU123");
        request.setQuantity(5);
        request.setUserName("John Doe");
        request.setUserEmail("john.doe@example.com");

        ItemEntity item = new ItemEntity(1L, "Item A");
        item.setSkuCode("SKU123");

        User user = new User(1L, "John Doe", "john.doe@example.com", null);

        Order order = new Order();
        order.setId(1L);
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(5);
        order.setStockMovements(Collections.emptyList());

        when(itemRepository.findBySkuCode(request.getSkuCode())).thenReturn(Optional.empty());
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(item);
        when(userRepository.findUserByEmail(request.getUserEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO response = orderService.create(request);

        assertNotNull(response);
        assertEquals("Item A", response.getItem().getName());
        assertEquals(5, response.getQuantity());
        assertNotNull(response.getStockMovements());
        assertTrue(response.getStockMovements().isEmpty());

        verify(itemRepository, times(1)).findBySkuCode(request.getSkuCode());
        verify(itemRepository, times(1)).save(any(ItemEntity.class));
        verify(userRepository, times(1)).findUserByEmail(request.getUserEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder_Success() throws Exception {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setId(1L);
        request.setQuantity(10);
        request.setSkuCode("SKU123");
        request.setItemName("Updated Item");

        ItemEntity item = new ItemEntity(1L, "Updated Item");
        item.setSkuCode("SKU123");

        User user = new User(1L, "John Doe", "john.doe@example.com", null);

        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setItem(item);
        existingOrder.setUser(user);
        existingOrder.setQuantity(5);
        existingOrder.setStockMovements(Collections.emptyList());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(itemRepository.findBySkuCode(request.getSkuCode())).thenReturn(Optional.of(item));
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        OrderResponseDTO response = orderService.update(request);

        assertNotNull(response);
        assertEquals(10, response.getQuantity());
        assertEquals("Updated Item", response.getItem().getName());
        assertNotNull(response.getStockMovements());
        assertTrue(response.getStockMovements().isEmpty());

        verify(orderRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).findBySkuCode(request.getSkuCode());
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void testFindOrderById_Success() {
        Long orderId = 1L;
        ItemEntity item = new ItemEntity(1L, "Item A");
        User user = new User(1L, "John Doe", "john.doe@example.com", null);
        Order order = new Order();
        order.setId(orderId);
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(5);
        order.setStockMovements(Collections.emptyList());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponseDTO response = orderService.findById(orderId);

        assertNotNull(response);
        assertEquals(5, response.getQuantity());
        assertNotNull(response.getStockMovements());
        assertTrue(response.getStockMovements().isEmpty());

        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testDeleteOrder_Success() {
        Long orderId = 1L;

        doNothing().when(orderRepository).deleteById(orderId);

        orderService.delete(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
