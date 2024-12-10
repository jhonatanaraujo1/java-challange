package com.challange.orderjavachallange.item.mapper;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.order.dto.ItemDTO;

public class ItemMapper {

    public static ItemDTO toDto(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        }
        return new ItemDTO(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getSkuCode()
        );
    }

    public static ItemEntity toEntity(ItemDTO itemDto) {
        if (itemDto == null) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemDto.getId());
        itemEntity.setName(itemDto.getName());
        itemEntity.setSkuCode(itemDto.getSkuCode());
        return itemEntity;
    }
}
