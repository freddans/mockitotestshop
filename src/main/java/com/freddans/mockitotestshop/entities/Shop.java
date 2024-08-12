package com.freddans.mockitotestshop.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int nrOfItems;

    @OneToMany
    private List<Item> itemList;

    public Shop() {
    }

    public Shop(String name) {
        this.name = name;
        this.nrOfItems = 0;
        this.itemList = new ArrayList<>();
    }

    public Shop(long id, String name, int nrOfItems) {
        this.id = id;
        this.name = name;
        this.nrOfItems = nrOfItems;
        this.itemList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void addItemToShop(Item item) {
        if (item != null) {
            this.itemList.add(item);
            this.nrOfItems += 1;
        }
    }

    public void removeItemFromShop(Item item) {
        if (item != null) {
            this.itemList.remove(item);
            this.nrOfItems -= 1;
        }
    }
}
