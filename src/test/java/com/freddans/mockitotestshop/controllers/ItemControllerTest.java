package com.freddans.mockitotestshop.controllers;

import com.freddans.mockitotestshop.entities.Item;
import com.freddans.mockitotestshop.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}