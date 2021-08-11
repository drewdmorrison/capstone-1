package com.techelevator;

import java.math.BigDecimal;

public abstract class Item {

    private String slot;
    private String name;
    private BigDecimal price;
    private int inventory;
    private int saleCounter;

    public Item(String slot, String name, BigDecimal price) {
        this.inventory = 5;
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.saleCounter = 0;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public int getSaleCounter() {
        return saleCounter;
    }

    public void increaseSaleCounter() {
        this.saleCounter = getSaleCounter() + 1;
    }

    public void lowerInventory() {
        this.inventory = getInventory() - 1;
    }

    public abstract String getSound();
}
