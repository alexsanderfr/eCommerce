package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            return ResponseEntity.ok(optionalItem.get());
        } else {
            log.info("ERROR: Item not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        List<Item> items = itemRepository.findByName(name);
        if (items != null && !items.isEmpty()) {
            return ResponseEntity.ok(items);
        } else {
            log.info("ERROR: Item not found");
            return ResponseEntity.notFound().build();
        }

    }

}
