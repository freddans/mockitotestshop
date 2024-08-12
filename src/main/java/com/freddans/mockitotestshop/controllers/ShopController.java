package com.freddans.mockitotestshop.controllers;

import com.freddans.mockitotestshop.entities.Shop;
import com.freddans.mockitotestshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Shop>> allShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> findShopById(@PathVariable("id") long id) {
        return ResponseEntity.ok(shopService.findShopById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.createShop(shop));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable("id") long id, @RequestBody Shop updatedShop) {
        return ResponseEntity.ok(shopService.updateShop(id, updatedShop));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable("id") long id) {
        return ResponseEntity.ok(shopService.deleteShop(id));
    }
}
