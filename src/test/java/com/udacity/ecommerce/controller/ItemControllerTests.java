package com.udacity.ecommerce.controller;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;


public class ItemControllerTests {
    private ItemController itemController;
    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController(itemRepository);
    }


    @Test
    public void findByIdTest() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Coca Cola");
        item.setDescription("A fizzy drink");
        item.setPrice(BigDecimal.ONE);
        Mockito.when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        ResponseEntity<Item> findItemResponseEntity = itemController.getItemById(item.getId());
        assertNotNull(findItemResponseEntity);
        assertEquals(HttpStatus.OK, findItemResponseEntity.getStatusCode());
        assertEquals(item, findItemResponseEntity.getBody());
    }

    @Test
    public void findByNameTest() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Coca Cola");
        item.setDescription("A fizzy drink");
        item.setPrice(BigDecimal.ONE);
        Mockito.when(itemRepository.findByName(item.getName())).thenReturn(Collections.singletonList(item));

        ResponseEntity<List<Item>> findItemResponseEntity = itemController.getItemsByName(item.getName());
        assertNotNull(findItemResponseEntity);
        assertEquals(HttpStatus.OK, findItemResponseEntity.getStatusCode());
        assertEquals(Collections.singletonList(item), findItemResponseEntity.getBody());
    }

    @Test
    public void findAllTest() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Coca Cola");
        item.setDescription("A fizzy drink");
        item.setPrice(BigDecimal.ONE);
        Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(item, item, item));

        ResponseEntity<List<Item>> findItemResponseEntity = itemController.getItems();
        assertNotNull(findItemResponseEntity);
        assertEquals(HttpStatus.OK, findItemResponseEntity.getStatusCode());
        assertEquals(Arrays.asList(item, item, item), findItemResponseEntity.getBody());
    }

}
