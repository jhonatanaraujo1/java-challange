package com.challange.orderjavachallange.order.repository;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.item = :item AND o.isComplete = false ORDER BY o.creationDate")
    List<Order> findPendingOrdersByItem(@Param("item") ItemEntity item);
}
