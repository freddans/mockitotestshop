package com.freddans.mockitotestshop.services;

import com.freddans.mockitotestshop.entities.Item;
import com.freddans.mockitotestshop.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        return optionalItem.orElse(null);
    }

    public Item findByName(String name) {
        Item item = itemRepository.findByName(name);
        if (item != null) {
            return itemRepository.findByName(name);
        } else {
            return null;
        }
    }

    public Item createItem(Item newItem) {
        // Check if item exist
        Item existingItem = findByName(newItem.getName());

        if (existingItem != null) {

            return null;
        } else {

            Item item = new Item(newItem.getName(), newItem.getCost());

            itemRepository.save(item);

            return item;
        }
    }

    public Item updateItem(long id, Item newItemInfo) {
        Item existingItem = findItemById(id);

        if (existingItem == null) {
            return null;
        } else {
            if (newItemInfo.getName() != null && !newItemInfo.getName().isEmpty() && newItemInfo.getName() != existingItem.getName()) {
                existingItem.setName(newItemInfo.getName());
            }
            if (newItemInfo.getCost() != 0 && newItemInfo.getCost() != existingItem.getCost()) {
                existingItem.setCost(newItemInfo.getCost());
            }

            itemRepository.save(existingItem);

            return existingItem;
        }
    }

    public String deleteItem(long id) {
        Item itemToDelete = findItemById(id);

        if (itemToDelete != null) {
            return "ERROR: Item could not be found with id: " + id + ".";
        } else {
            itemRepository.delete(itemToDelete);
            return "Item deleted";
        }
    }
}
