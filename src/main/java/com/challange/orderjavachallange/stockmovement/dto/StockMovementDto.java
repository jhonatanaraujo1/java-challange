package com.challange.orderjavachallange.stockmovement.dto;

import java.time.LocalDateTime;

public class StockMovementDto {
    private Long id;
    private int quantity;
    private Long itemId;
    private String itemName;
    private Long orderId;

    public StockMovementDto() {
    }

    public StockMovementDto(Long id, LocalDateTime creationDate, int quantity, Long itemId, String itemName, Long orderId) {
        this.id = id;
        this.quantity = quantity;
        this.itemId = itemId;
        this.itemName = itemName;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
