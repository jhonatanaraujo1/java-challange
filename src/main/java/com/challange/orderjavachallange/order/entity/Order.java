package com.challange.orderjavachallange.order.entity;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import com.challange.orderjavachallange.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    private boolean isComplete = false;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<StockMovement> stockMovements;

    public Order() {
    }

    public Order(Long id, LocalDateTime creationDate, int quantity, boolean isComplete, ItemEntity item, User user, List<StockMovement> stockMovements) {
        this.id = id;
        this.creationDate = creationDate;
        this.quantity = quantity;
        this.isComplete = isComplete;
        this.item = item;
        this.user = user;
        this.stockMovements = stockMovements;
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }
}
