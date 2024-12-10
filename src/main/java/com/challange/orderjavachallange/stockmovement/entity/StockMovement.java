package com.challange.orderjavachallange.stockmovement.entity;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.order.entity.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate = LocalDateTime.now();

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public StockMovement() {
    }
    public StockMovement(Long id, LocalDateTime creationDate, int quantity, ItemEntity item, Order order) {
        this.id = id;
        this.creationDate = creationDate;
        this.quantity = quantity;
        this.item = item;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
