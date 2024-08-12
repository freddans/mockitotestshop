package com.freddans.mockitotestshop.services;

import com.freddans.mockitotestshop.entities.Shop;
import com.freddans.mockitotestshop.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop findShopById(long id) {
        Optional<Shop> optionalShop = shopRepository.findById(id);

        return optionalShop.orElse(null);
    }

    public Shop findShopByName(String name) {
        Shop shop = shopRepository.findByName(name);
        if (shop != null) {

            return shop;
        } else {

            return null;
        }
    }

    public Shop createShop(Shop shop) {
        Shop existingShop = findShopByName(shop.getName());

        if (existingShop != null) {

            if (shop.getName() != null && !shop.getName().isEmpty()) {
                Shop newShop = new Shop(shop.getName());

                shopRepository.save(newShop);

                return newShop;
            } else {
                // Shop name is either null or empty
                return null;
            }
        } else {

            // Shop already exist
            return null;
        }
    }

    public Shop updateShop(long id, Shop newShopInfo) {
        Shop existingShop = findShopById(id);

        if (existingShop != null) {
            if (newShopInfo.getName() != null && !newShopInfo.getName().isEmpty() && newShopInfo.getName() != existingShop.getName()) {

                existingShop.setName(newShopInfo.getName());

                shopRepository.save(existingShop);
            }

            return existingShop;
        } else {

            // Shop cant be found
            return null;
        }
    }

    public String deleteShop(long id) {
        Shop shopToDelete = findShopById(id);

        if (shopToDelete != null) {
            shopRepository.delete(shopToDelete);

            return "Shop deleted";
        } else {

            // Shop not found
            return "Shop not found";
        }
    }
}
