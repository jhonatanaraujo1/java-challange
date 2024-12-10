package com.challange.orderjavachallange.unit.item.mapper;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.mapper.ItemMapper;
import com.challange.orderjavachallange.order.dto.ItemDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    @Test
    void testToDto_WithValidItemEntity() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Item A");
        itemEntity.setSkuCode("SKU123");

        ItemDTO itemDTO = ItemMapper.toDto(itemEntity);

        assertNotNull(itemDTO);
        assertEquals(itemEntity.getId(), itemDTO.getId());
        assertEquals(itemEntity.getName(), itemDTO.getName());
        assertEquals(itemEntity.getSkuCode(), itemDTO.getSkuCode());
    }

    @Test
    void testToDto_WithNullItemEntity() {
        ItemDTO itemDTO = ItemMapper.toDto(null);

        assertNull(itemDTO);
    }

    @Test
    void testToEntity_WithValidItemDTO() {
        ItemDTO itemDTO = new ItemDTO(1L, "Item A", "SKU123");

        ItemEntity itemEntity = ItemMapper.toEntity(itemDTO);

        assertNotNull(itemEntity);
        assertEquals(itemDTO.getId(), itemEntity.getId());
        assertEquals(itemDTO.getName(), itemEntity.getName());
        assertEquals(itemDTO.getSkuCode(), itemEntity.getSkuCode());
    }

    @Test
    void testToEntity_WithNullItemDTO() {
        ItemEntity itemEntity = ItemMapper.toEntity(null);

        assertNull(itemEntity);
    }
}
