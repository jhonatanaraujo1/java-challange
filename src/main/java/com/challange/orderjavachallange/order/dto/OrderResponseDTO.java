package com.challange.orderjavachallange.order.dto;


import com.challange.orderjavachallange.stockmovement.dto.StockMovementDto;
import com.challange.orderjavachallange.user.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderResponseDTO {

    private Long id;
    private ItemDTO item;
    private UserDto user;
    private int quantity;
    private String status;
    private LocalDateTime createdAt;
    private List<StockMovementDto> stockMovements;

    public OrderResponseDTO(Long id, ItemDTO item, UserDto user, int quantity, String status, LocalDateTime createdAt, List<StockMovementDto> stockMovements) {
        this.id = id;
        this.item = item;
        this.user = user;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
        this.stockMovements = stockMovements;
    }

    public OrderResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long od) {
        this.id = id;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<StockMovementDto> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovementDto> stockMovements) {
        this.stockMovements = stockMovements;
    }
}
