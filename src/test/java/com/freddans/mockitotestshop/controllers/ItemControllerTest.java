package com.freddans.mockitotestshop.controllers;

import com.freddans.mockitotestshop.entities.Item;
import com.freddans.mockitotestshop.repositories.ItemRepository;
import com.freddans.mockitotestshop.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemControllerTest {

    private ItemService itemServiceMock;
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        // Mock the itemService
        itemServiceMock = mock(ItemService.class);
        itemController = new ItemController(itemServiceMock);
    }

    @Test
    void allItems() {
        String itemName = "TestItem";
        double itemCost = 100.0;

        Item itemMock = mock(Item.class);

        when(itemMock.getName()).thenReturn(itemName);
        when(itemMock.getCost()).thenReturn(itemCost);

        List<Item> mockItemsList = Arrays.asList(itemMock);

        when(itemServiceMock.getAllItems()).thenReturn(mockItemsList);

        ResponseEntity<List<Item>> response = itemController.allItems();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(itemName, response.getBody().get(0).getName());
        assertEquals(100.0, response.getBody().get(0).getCost());

        verify(itemServiceMock).getAllItems();
    }

    @Test
    void itemByName() {
        // Arrange
        String itemName = "TestItem";

        // Create mock item instance
        Item itemMock = mock(Item.class);

        // Define behavior of the mock item and itemService
        when(itemMock.getName()).thenReturn(itemName);
        when(itemMock.getCost()).thenReturn(100.0);
        when(itemServiceMock.findByName(itemName)).thenReturn(itemMock);

        // Act
        ResponseEntity<Item> response = itemController.itemByName(itemName);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(itemName, response.getBody().getName());
        assertEquals(100.0, response.getBody().getCost());

        // Verify
        verify(itemServiceMock).findByName(itemName);
    }

    @Test
    void itemById() {
        // Given
        long itemId = 1;
        String itemName = "ItemName";
        double itemCost = 100.0;

        Item itemMock = mock(Item.class);

        when(itemMock.getId()).thenReturn(itemId);
        when(itemMock.getName()).thenReturn(itemName);
        when(itemMock.getCost()).thenReturn(itemCost);
        when(itemServiceMock.findItemById(itemId)).thenReturn(itemMock);

        // When
        Item responseItem = itemServiceMock.findItemById(itemId);

        // Then
        assertEquals(responseItem.getId(), itemId, "Unexpected answer");

        verify(itemServiceMock).findItemById(itemId);
    }

    @Test
    void createItem() {
        String itemName = "ItemName";
        double cost = 200.0;

        Item itemMock = mock(Item.class);

        when(itemMock.getName()).thenReturn(itemName);
        when(itemMock.getCost()).thenReturn(cost);

        when(itemServiceMock.createItem(itemMock)).thenReturn(itemMock);

        ResponseEntity<Item> response = itemController.createItem(itemMock);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(itemName, response.getBody().getName());
        assertEquals(cost, response.getBody().getCost());

        verify(itemServiceMock).createItem(itemMock);
    }

    @Test
    void updateItem() {
        // Give
        Item existingItemMock = mock(Item.class);

        long existingId = 1;

        Item updatedItemMock = mock(Item.class);
        long updatedId = existingId;
        String updatedItemName = "Updated";
        double updatedItemCost = 200.0;

        when(existingItemMock.getId()).thenReturn(updatedId); // When asking for existing id, give updated id
        when(existingItemMock.getName()).thenReturn(updatedItemName); // When asking for existing name, get updated name
        when(existingItemMock.getCost()).thenReturn(updatedItemCost); // when asking for existing cost, get updated cost

        // When
        ResponseEntity<Item> actual = itemController.updateItem(existingId, updatedItemMock);

        // Then
        assertEquals(200, actual.getStatusCodeValue(), "Unexpected status code"); // status code should be 200
        assertEquals(existingId, updatedId, "Unexpected id"); // ID should be the same
        assertEquals(updatedItemName, existingItemMock.getName(), "Unexpected name"); // updated name should be same as existing items name
        assertEquals(200, existingItemMock.getCost(), "Unexpected cost"); // cost should be 200.


        verify(itemServiceMock).updateItem(updatedId, updatedItemMock);
    }

    @Test
    void deleteItem() {
        // Create a mocked item
        long itemId = 1;
        String expectedMessage = "Item deleted";

        when(itemServiceMock.deleteItem(itemId)).thenReturn(expectedMessage);

        ResponseEntity<String> actualMessage = itemController.deleteItem(itemId);

        assertEquals(200, actualMessage.getStatusCodeValue(), "Expected HTTP status code to be 200");
        assertEquals(expectedMessage, actualMessage.getBody(), "The response Body should contain the expected message");

        verify(itemServiceMock).deleteItem(itemId);
    }

}