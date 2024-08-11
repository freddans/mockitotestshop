package com.freddans.mockitotestshop.controllers;

import com.freddans.mockitotestshop.entities.Item;
import com.freddans.mockitotestshop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> allItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Item> itemByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(itemService.findByName(name));
    }

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.createItem(item));
    }
}
