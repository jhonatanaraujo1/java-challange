package com.challange.orderjavachallange.stockmovement.repository;

import com.challange.orderjavachallange.stockmovement.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    @Query("SELECT sm FROM StockMovement sm " +
            "WHERE sm.item.id = :itemId AND sm.order IS NULL " +
            "ORDER BY sm.creationDate ASC")
    List<StockMovement> findAvailableStocksForItem(@Param("itemId") Long itemId);
}
