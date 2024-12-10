package com.challange.orderjavachallange.unit.order.mapper;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.mapper.ItemMapper;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;
import com.challange.orderjavachallange.order.entity.Order;
import com.challange.orderjavachallange.order.mapper.OrderMapper;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.stockmovement.mapper.StockMovementMapper;
import com.challange.orderjavachallange.user.entity.User;
import com.challange.orderjavachallange.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OrderMapperTest {

    @Test
    void testToEntity_WithValidInput() {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("Item A");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setQuantity(5);

        Order order = OrderMapper.toEntity(requestDTO, item, user);

        assertNotNull(order);
        assertEquals(5, order.getQuantity());
        assertEquals(item, order.getItem());
        assertEquals(user, order.getUser());
    }

    @Test
    void testToDTO_WithValidOrder() {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("Item A");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setQuantity(5);

        Order order = new Order();
        order.setId(1L);
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(10);
        order.setComplete(true);
        order.setCreationDate(LocalDateTime.now());
        order.setStockMovements(List.of(stockMovement));

        ItemMapper itemMapper = mock(ItemMapper.class);
        UserMapper userMapper = mock(UserMapper.class);
        StockMovementMapper stockMovementMapper = mock(StockMovementMapper.class);

        OrderResponseDTO responseDTO = OrderMapper.toDTO(order);

        assertNotNull(responseDTO);
        assertEquals(order.getItem().getId(), responseDTO.getItem().getId());
        assertEquals(order.getUser().getId(), responseDTO.getUser().getId());
        assertEquals(order.getQuantity(), responseDTO.getQuantity());
        assertEquals("COMPLETED", responseDTO.getStatus());
        assertNotNull(responseDTO.getCreatedAt());
    }

    @Test
    void testToDTO_WithNullOrder() {
        OrderResponseDTO responseDTO = OrderMapper.toDTO(null);

        assertNull(responseDTO);
    }
}
