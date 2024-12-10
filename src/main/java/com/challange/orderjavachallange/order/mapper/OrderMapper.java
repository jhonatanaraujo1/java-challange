package com.challange.orderjavachallange.order.mapper;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.mapper.ItemMapper;
import com.challange.orderjavachallange.stockmovement.mapper.StockMovementMapper;
import com.challange.orderjavachallange.user.mapper.UserMapper;
import com.challange.orderjavachallange.order.dto.OrderRequestDTO;
import com.challange.orderjavachallange.order.entity.Order;
import com.challange.orderjavachallange.user.entity.User;
import com.challange.orderjavachallange.order.dto.OrderResponseDTO;

import java.util.List;

public class OrderMapper {

    public static Order toEntity(OrderRequestDTO dto, ItemEntity item, User user) {
        Order order = new Order();
        order.setQuantity(dto.getQuantity());
        order.setItem(item);
        order.setUser(user);
        return order;
    }

    public static OrderResponseDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setItem(order.getItem() != null ? ItemMapper.toDto(order.getItem()) : null);
        dto.setUser(order.getUser() != null ? UserMapper.toDto(order.getUser()) : null);
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.isComplete() ? "COMPLETED" : "PENDING");
        dto.setCreatedAt(order.getCreationDate());
        dto.setStockMovements(
                order.getStockMovements() != null
                        ? order.getStockMovements().stream()
                        .filter(stock -> stock.getItem() != null)
                        .map(StockMovementMapper::toDto)
                        .toList()
                        : List.of()
        );
        return dto;
    }

}
