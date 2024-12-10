package com.challange.orderjavachallange.item.repository;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findBySkuCode(String skuCode);
}
