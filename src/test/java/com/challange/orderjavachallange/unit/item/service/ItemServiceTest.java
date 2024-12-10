package com.challange.orderjavachallange.unit.item.service;

import com.challange.orderjavachallange.item.entity.ItemEntity;
import com.challange.orderjavachallange.item.repository.ItemRepository;
import com.challange.orderjavachallange.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    public ItemServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        ItemEntity item1 = new ItemEntity();
        item1.setId(1L);
        item1.setName("Item A");

        ItemEntity item2 = new ItemEntity();
        item2.setId(2L);
        item2.setName("Item B");

        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<ItemEntity> items = itemService.getAllItems();

        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Item A", items.get(0).getName());
        assertEquals("Item B", items.get(1).getName());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testCreateItem() {
        ItemEntity item = new ItemEntity();
        item.setId(1L);
        item.setName("New Item");

        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity createdItem = itemService.createItem(item);

        assertNotNull(createdItem);
        assertEquals("New Item", createdItem.getName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testDeleteItem() {
        Long itemId = 1L;
        itemService.deleteItem(itemId);
        verify(itemRepository, times(1)).deleteById(itemId);
    }
}
