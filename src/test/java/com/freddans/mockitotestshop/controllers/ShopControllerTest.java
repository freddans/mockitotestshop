package com.freddans.mockitotestshop.controllers;

import com.freddans.mockitotestshop.entities.Item;
import com.freddans.mockitotestshop.entities.Shop;
import com.freddans.mockitotestshop.services.ShopService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopControllerTest {

    private ShopService shopServiceMock;
    private ShopController shopController;

    @BeforeEach
    void setUp() {
        shopServiceMock = mock(ShopService.class);
        shopController = new ShopController(shopServiceMock);
    }

    @Test
    void allShops() {
        String shopName = "ICA";
        Shop shopMock = mock(Shop.class);

        when(shopMock.getName()).thenReturn(shopName);

        List<Shop> shopList = Arrays.asList(shopMock);

        when(shopServiceMock.getAllShops()).thenReturn(shopList);

        ResponseEntity<List<Shop>> actualResponse = shopController.allShops();

        assertEquals(200, actualResponse.getStatusCodeValue(), "Did not go through");
        assertEquals(1, shopList.size(), "Shoplist size was more than expected");
        assertEquals(shopMock.getName(), shopList.get(0).getName(), "Shopname was different");

        verify(shopServiceMock).getAllShops();
    }

    @Test
    void findShopById() {
        long shopId = 1;
        String shopName = "Willys";
        List<Item> shopEmptyItemsList = Collections.emptyList();
        int itemAmount = 0;

        Shop shopMock = mock(Shop.class);

        when(shopMock.getId()).thenReturn(shopId);
        when(shopMock.getName()).thenReturn(shopName);
        when(shopMock.getItemList()).thenReturn(shopEmptyItemsList);
        when(shopMock.getNrOfItems()).thenReturn(itemAmount);
        when(shopServiceMock.findShopById(shopId)).thenReturn(shopMock);

        // When
        ResponseEntity<Shop> actualShop = shopController.findShopById(shopId);

        assertEquals(200, actualShop.getStatusCodeValue(), "Unexpected status code");
        assertEquals(shopId, actualShop.getBody().getId(), "Unexpected id");
        assertEquals(shopName, actualShop.getBody().getName(), "Unexpected name");
        assertEquals(shopEmptyItemsList, actualShop.getBody().getItemList(), "Unexpected list");
        assertEquals(itemAmount, actualShop.getBody().getNrOfItems(), "Unexpected nr of items");
        assertEquals(shopMock, actualShop.getBody(), "Unexpected shop");

        verify(shopServiceMock).findShopById(shopId);
    }

    @Test
    void createShop() {
        long shopId = 1;
        String shopName = "IKEA";
        Shop shopMock = mock(Shop.class);

        when(shopMock.getId()).thenReturn(shopId);
        when(shopMock.getName()).thenReturn(shopName);
        when(shopServiceMock.createShop(shopMock)).thenReturn(shopMock);

        ResponseEntity<Shop> actualShop = shopController.createShop(shopMock);

        assertEquals(200, actualShop.getStatusCodeValue(), "Unexpected status code");
        assertEquals(shopId, actualShop.getBody().getId(), "Unexpected id");
        assertEquals(shopName, actualShop.getBody().getName(), "Unexpected name");

        verify(shopServiceMock).createShop(shopMock);
    }

    @Test
    void updateShop() {
        long existingShopId = 1;

        Shop existingShopMock = mock(Shop.class);
        when(existingShopMock.getId()).thenReturn(existingShopId);

        String updatedShopName = "Hemköp";
        List<Item> updatedShopList = Collections.emptyList();
        int updatedShopItems = 0;

        Shop updatedShopMock = mock(Shop.class);

        when(updatedShopMock.getId()).thenReturn(existingShopId);
        when(updatedShopMock.getName()).thenReturn(updatedShopName);
        when(updatedShopMock.getItemList()).thenReturn(updatedShopList);
        when(updatedShopMock.getNrOfItems()).thenReturn(updatedShopItems);
        when(shopServiceMock.updateShop(existingShopId, updatedShopMock)).thenReturn(updatedShopMock);

        ResponseEntity<Shop> actualResponse = shopController.updateShop(existingShopId, updatedShopMock);

        assertEquals(200, actualResponse.getStatusCodeValue(), "Unexpected actual test - status code");
        assertEquals(existingShopId, actualResponse.getBody().getId(), "Unexpected actual test - id");
        assertEquals(updatedShopName, actualResponse.getBody().getName(), "Unexpected actual test - name");
        assertEquals(updatedShopList, actualResponse.getBody().getItemList(), "Unexpected actual test - shop list");
        assertEquals(updatedShopItems, actualResponse.getBody().getNrOfItems(), "Unexpected actual test - nr of items");

        verify(shopServiceMock).updateShop(existingShopId, updatedShopMock);
    }

    @Test
    void deleteShop() {
        long shopId = 1;
        String shopName = "Coop";
        List<Item> emptyItemsList = Collections.emptyList();
        int shopItems = 0;

        Shop shopMock = mock(Shop.class);

        String expectedMessage = "Shop deleted";

        when(shopMock.getId()).thenReturn(shopId);
        when(shopMock.getName()).thenReturn(shopName);
        when(shopMock.getItemList()).thenReturn(emptyItemsList);
        when(shopMock.getNrOfItems()).thenReturn(shopItems);

        when(shopServiceMock.deleteShop(shopId)).thenReturn(expectedMessage);

        ResponseEntity<String> actualResponse = shopController.deleteShop(shopId);

        assertEquals(200, actualResponse.getStatusCodeValue(), "Unexpected status code");
        assertEquals(shopId, shopMock.getId(), "Unexpected shop ID");
        assertEquals(shopName, shopMock.getName(), "Unexpected name");
        assertEquals(emptyItemsList, shopMock.getItemList(), "unexpected list");
        assertEquals(shopItems, shopMock.getNrOfItems(), "Unexpected nr of items in the shops itemlist");
        assertEquals(expectedMessage, actualResponse.getBody(), "Shop not deleted");

        verify(shopServiceMock).deleteShop(shopId);
    }
}